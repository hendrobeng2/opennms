package org.opennms.web.rest.rrd.fetch;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.jrobin.core.RrdException;
import org.opennms.core.utils.StringUtils;
import org.opennms.netmgt.dao.api.ResourceDao;
import org.opennms.netmgt.model.OnmsResource;
import org.opennms.netmgt.model.RrdGraphAttribute;
import org.opennms.netmgt.rrd.model.RrdXport;
import org.opennms.netmgt.rrd.model.XRow;
import org.opennms.web.rest.rrd.QueryRequest;
import org.opennms.web.rest.rrd.QueryRequest.Source;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.common.collect.Maps;

public class JniRrdFetchStrategy implements RrdFetchStrategy {
	private final ResourceDao m_resourceDao;

	public JniRrdFetchStrategy(final ResourceDao resourceDao) {
		m_resourceDao = resourceDao;
	}

	@Override
	public SortedMap<Long, Map<String, Double>> fetch(long step, long start,
			long end, List<Source> sources) throws RrdException {
		String rrdBinary = System.getProperty("rrd.binary");

        if (rrdBinary == null) {
            throw new RrdException("rrd.binary property must be set either in opennms.properties or in iReport");
        }

        //construct the query string out of the requestedMetrics data
        final StringBuilder query = new StringBuilder();
        query.append("--step").append(" ")
                .append(step).append(" ");

        query.append("--start").append(" ")
                .append(start).append(" ");

        query.append("--end").append(" ")
                .append(end).append(" ");

        for (final QueryRequest.Source source : sources) {
            final OnmsResource resource = m_resourceDao.getResourceById(source.getResource());
            final RrdGraphAttribute rrdGraphAttribute = resource.getRrdGraphAttributes().get(source.getAttribute());

            final String rrdFile = System.getProperty("rrd.base.dir") + File.separator + rrdGraphAttribute.getRrdRelativePath();

            query.append("DEF:")
                    .append(source.getLabel())
                    .append("=")
                    .append(rrdFile)
                    .append(":")
                    .append(source.getAttribute())
                    .append(":")
                    .append(source.getAggregation())
                    .append(" ");
        }

        StringBuilder command = new StringBuilder();
        command.append(rrdBinary).append(" ");
        command.append("xport").append(" ");
        command.append(query
                .toString()
                .replaceAll("[\r\n]+", " ")
                .replaceAll("\\s+", " "));

        String[] commandArray = StringUtils.createCommandArray(command.toString(), '@');

        // TODO: Make sure streams get closed
        // TODO: Use commons-exec
        RrdXport rrdXport;
        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            Process process = Runtime.getRuntime().exec(commandArray);
            SAXSource source = new SAXSource(xmlReader, new InputSource(new InputStreamReader(process.getInputStream())));
            JAXBContext jc = JAXBContext.newInstance(RrdXport.class);
            Unmarshaller u = jc.createUnmarshaller();
            rrdXport = (RrdXport) u.unmarshal(source);
        } catch (Exception e) {
            throw new RrdException("Can't parse RRD export", e);
        }

        SortedMap<Long, Map<String, Double>> results = new TreeMap<Long, Map<String, Double>>();
        for (XRow row : rrdXport.getRows()) {
            Map<String, Double> values = Maps.newHashMap();
            int k = 0;
            for (String column : rrdXport.getMeta().getLegends()) {
                values.put(column, row.getValues().get(k++));
            }
            results.put(row.getTimestamp(), values);
        }

        return results;
	}
}

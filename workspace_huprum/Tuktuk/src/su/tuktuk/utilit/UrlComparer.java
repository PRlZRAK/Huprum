package su.tuktuk.utilit;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class UrlComparer
{
    private boolean hostIsCaseSensitive = false;
    private boolean pathIsCaseSensitive = true;
    private boolean queryStringKeysAreCaseSensitive = true;
    private boolean queryStringValuesAreCaseSensitive = false;
    private boolean schemeIsCaseSensitive = false;

    public static void main(String[] args) 
    {
    	UrlComparer urlComparer = new UrlComparer();

      
       System.out.println(urlComparer.urlsMatch("http://stackoverflow.com/questions", "https://stackoverflow.com/questions"));
       System.out.println(urlComparer.urlsMatch("https://stackoverflow.com/questions/", "https://stackoverflow.com/questions"));
       System.out.println(urlComparer.urlsMatch("https://tuktuk.su/huprum/server/img/long_test", "http://tuktuk.su/huprum/server/img/long_test"));
       System.out.println(urlComparer.urlsMatch("http://TUKTUK.SU/huprum/server/img/long_test", "http://tuktuk.su/huprum/server/img/long_test"));
      }
     
  
    public boolean urlsMatch(String url1, String url2)
    {
        try
        {
            if (Objects.equals(url1, url2))
                return true;

            URI uri1 = new URI(url1);
            URI uri2 = new URI(url2);

            // Compare Query String Parameters
            Map<String, String> mapParams1 = getQueryStringParams(uri1);
            Map<String, String> mapParams2 = getQueryStringParams(uri2);
            if (!mapsAreEqual(mapParams1, mapParams2, getQueryStringValuesAreCaseSensitive()))
                return false;

            // Compare scheme (http or https)
            if (!stringsAreEqual(uri1.getScheme(), uri2.getScheme(), getSchemeIsCaseSensitive()))
                return false;

            // Compare host
            if (!stringsAreEqual(uri1.getHost(), uri2.getHost(), getHostIsCaseSensitive()))
                return false;

            // Compare path
            if (!stringsAreEqual(uri1.getPath(), uri2.getPath(), getPathIsCaseSensitive()))
                return false;

            // Compare ports
            if (!portsAreEqual(uri1, uri2))
                return false;

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    protected Map<String, String> getQueryStringParams(URI uri)
    {
        Map<String, String> result = getListAsMap(URLEncodedUtils.parse(uri, "UTF-8"), getQueryStringKeysAreCaseSensitive());

        return result;
    }

    protected boolean stringsAreEqual(String s1, String s2, boolean caseSensitive)
    {
        // Eliminate null cases
        if (s1 == null || s2 == null)
        {
            if (s1 == s2)
                return true;

            return false;
        }

        if (caseSensitive)
        {
            return s1.equals(s2);
        }

        return s1.equalsIgnoreCase(s2);
    }

    protected boolean mapsAreEqual(Map<String, String> map1, Map<String, String> map2, boolean caseSensitiveValues)
    {
        for (Map.Entry<String, String> entry : map1.entrySet())
        {
            String key = entry.getKey();
            String map1value = entry.getValue();
            String map2value = map2.get(key);

            if (!stringsAreEqual(map1value, map2value, caseSensitiveValues))
                return false;
        }
        for (Map.Entry<String, String> entry : map2.entrySet())
        {
            String key = entry.getKey();
            String map2value = entry.getValue();
            String map1value = map2.get(key);

            if (!stringsAreEqual(map1value, map2value, caseSensitiveValues))
                return false;
        }

        return true;
    }

    protected boolean portsAreEqual(URI uri1, URI uri2)
    {
        int port1 = uri1.getPort();
        int port2 = uri2.getPort();

        if (port1 == port2)
            return true;

        if (port1 == -1)
        {
            String scheme1 = (uri1.getScheme() == null ? "http" : uri1.getScheme()).toLowerCase();
            port1 = scheme1.equals("http") ? 80 : 443;
        }
        if (port2 == -1)
        {
            String scheme2 = (uri2.getScheme() == null ? "http" : uri2.getScheme()).toLowerCase();
            port2 = scheme2.equals("http") ? 80 : 443;
        }

        boolean result = (port1 == port2);

        return result;
    }

    protected Map<String, String> getListAsMap(List<NameValuePair> list, boolean caseSensitiveKeys)
    {
        Map<String, String> result;

        if (caseSensitiveKeys)
        {
            result = new HashMap<String, String>();
        }
        else
        {
            result = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        }

        for (NameValuePair param : list)
        {
            if (caseSensitiveKeys)
            {
                if (!result.containsKey(param.getName()))
                    result.put(param.getName(), param.getValue());
            }
            else
            {
                result.put(param.getName(), param.getValue());
            }
        }

        return result;
    }

    public boolean getSchemeIsCaseSensitive()
    {
        return schemeIsCaseSensitive;
    }

    public void setSchemeIsCaseSensitive(boolean schemeIsCaseSensitive)
    {
        this.schemeIsCaseSensitive = schemeIsCaseSensitive;
    }

    public boolean getHostIsCaseSensitive()
    {
        return hostIsCaseSensitive;
    }

    public void setHostIsCaseSensitive(boolean hostIsCaseSensitive)
    {
        this.hostIsCaseSensitive = hostIsCaseSensitive;
    }

    public boolean getPathIsCaseSensitive()
    {
        return pathIsCaseSensitive;
    }

    public void setPathIsCaseSensitive(boolean pathIsCaseSensitive)
    {
        this.pathIsCaseSensitive = pathIsCaseSensitive;
    }

    public boolean getQueryStringKeysAreCaseSensitive()
    {
        return queryStringKeysAreCaseSensitive;
    }

    public void setQueryStringKeysAreCaseSensitive(boolean queryStringKeysAreCaseSensitive)
    {
        this.queryStringKeysAreCaseSensitive = queryStringKeysAreCaseSensitive;
    }

    public boolean getQueryStringValuesAreCaseSensitive()
    {
        return queryStringValuesAreCaseSensitive;
    }

    public void setQueryStringValuesAreCaseSensitive(boolean queryStringValuesAreCaseSensitive)
    {
        this.queryStringValuesAreCaseSensitive = queryStringValuesAreCaseSensitive;
    }

}
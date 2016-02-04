package proxy;

import java.net.Socket;
import java.util.HashMap;

public class MyProxy extends PrivacyProxy {

    //////////////////////////////////////////////////////////////////////////
    //
    // Enhance your proxy by implementing the following three methods:
    //   - onRequest
    //   - onResponse
    //
    //////////////////////////////////////////////////////////////////////////

    protected HashMap<String, String> onRequest(HashMap<String, String> requestHeaders, String url){
    // This is the onRequest handler.
    // It will be executed whenever an HTTP request passes by.
    // Its arguments are a so-called HashMap containg all headers from the request, and a simple String containing the requested URL.
    // You can put code here to print the request headers, and to modify them.

        // let's simply print the requested URL, for a start that's enough:
        //log("Request for: " + url);
        //if(url.contains("syndication") || url.contains("scorecardresearch") || url.contains("googlead"))
        //    return null;

        // if we want to print all the request headers , use the below code:
        // it does a for-loop over all headers


        // example code to do something if a certain requestheader is present:

        for(String s : requestHeaders.keySet()){
            if(s.contains("Encoding")){
                requestHeaders.put(s, "deflate");
                //System.out.println("hereeeee " + requestHeaders.get(s));
                System.out.println("the encoding is now " + requestHeaders.get(s));
            }
            if(s.contains("User") && s.contains("Agent")) {
                requestHeaders.put(s,"You are not allowed to view my user agent");
            }
        }
        requestHeaders.remove("Referer");
        requestHeaders.remove("Cookie");
        requestHeaders.remove("Cache-Control");
        requestHeaders.remove("Pragma");
        for (String header : requestHeaders.keySet()) {
            // within the for loop, the variable  header  contains the name of the header
            // and you can ask for the contents of that header using requestHeaders.get() .
           log("  REQ: " + header + ": " + requestHeaders.get(header));
        }

        // example code to remove the  Creepyness  header:
/*
        requestHeaders.remove("Creepyness");
*/

        // example code to insert (or replace) the  Niceness  header:
/*
        requestHeaders.put("Niceness","high");
*/
        // return the (manipulated) headers, or
        return requestHeaders;

        // alternatively, drop this request by returning null
        // return null;
    }


    protected byte[] onResponse(byte[] originalBytes, String httpresponse){
    // This is the onResponse handler.
    // It will be executed whenever an HTTP reply passes by.
    // Its arguments are the entire HTTP response (both headers and data) as a byte array, and the line containing the response code.
    // For your convenience, the response headers are also available as a HashMap called responseHeaders , but you can't modify it.
        //log("Response: "+httpresponse);

        // if you want to (safely, i.e., without binary garbage) print the entire response, uncomment the following:

        String htmlCode = null;
        if (responseHeaders.containsKey("Content-Type") && responseHeaders.get("Content-Type").startsWith("text/html")) {
            htmlCode = new String(originalBytes);
            htmlCode = htmlCode.replace("<script type=\"text/javascript\" src=\"http://apis.google.com/js/plusone.js\"></script>", "");
            htmlCode = htmlCode.replaceAll("<iframe src=\"http://www\\.facebook\\.com/plugins/like\\.php.*?</iframe>", "");
            htmlCode = htmlCode.replace("<script type=\"text/javascript\" src=\"yolo.js\"></script>", "");
            htmlCode = htmlCode.replaceAll("<div id=\"ad\"><img src=\"http://shackle.nl/spy.png\"></div>", "");
            log(htmlCode);
        }
        return htmlCode == null ? originalBytes : htmlCode.getBytes();



        // if you want to modify the response, you can either modify the byte array directly,
        // or first convert it to a string and then modify that, _if_ you know for sure the response is in text form
        // (otherwise, a string doesn't make sense).
/*
        if (responseHeaders.containsKey("Content-Type") && responseHeaders.get("Content-Type").startsWith("text/html")) {
             String s = new String(originalBytes);
             String s2 = s.replaceAll("headers", "");
             byte [] alteredBytes = s2.getBytes();
             log("L: "+originalBytes.length);
             //responseLength = s2.length();
             log("L: "+alteredBytes.length);
             return alteredBytes;
        }
*/
        // return the original, unmodified array:
    }

    
    // Constructor, no need to touch this
    public MyProxy(Socket socket, Boolean autoFlush) {
        super(socket, autoFlush);
    }
}

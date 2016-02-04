package proxy;

import java.net.Socket;
import java.util.HashMap;

public class MyProxy extends PrivacyProxy {

	
	private String[] blockList = {"analytics", "ads", "advertisement"};
	
	
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
        log("Request for: " + url);
        
        // if we want to print all the request headers , use the below code:
        // it does a for-loop over all headers
        for (String header : requestHeaders.keySet()) {
            // within the for loop, the variable  header  contains the name of the header
            // and you can ask for the contents of that header using requestHeaders.get() .
            log("  REQ: " + header + ": " + requestHeaders.get(header));
        }

       // if (requestHeaders.containsKey("MyHeader")) {

	        // example code to remove the  Creepyness  header:
	
	        requestHeaders.remove("User-Agent");
	        requestHeaders.remove("Accept-Encoding");
	        requestHeaders.remove("Referer");
	        requestHeaders.remove("Cookie");
	        requestHeaders.remove("Cache-Control");
	        requestHeaders.remove("Pragma");
	        // example code to insert (or replace) the  Niceness  header:
	
	        // requestHeaders.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
	        requestHeaders.put("Accept-Encoding", "delfate");
      //  }
        if (url.contains("syndication") || url.contains("googleads") || 
        		url.contains("scorecardresearch") || url.contains("iperceptions") || 
        		url.contains("optimizely") || url.contains("effectivemeasure")) {
			return null;
		}
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
        log("Response: "+httpresponse);

        // if you want to (safely, i.e., without binary garbage) print the entire response, uncomment the following:

        //System.out.println(new String(originalBytes));
        
        //printSafe(originalBytes);


        // if you want to modify the response, you can either modify the byte array directly,
        // or first convert it to a string and then modify that, _if_ you know for sure the response is in text form
        // (otherwise, a string doesn't make sense).
        /*
        if (responseHeaders.containsKey("Content-Type") && responseHeaders.get("Content-Type").startsWith("text/html")) {
             String s = new String(originalBytes);
             String s2 = s.replaceAll("headers", " // if you want to (safely, i.e., without binary garbage) print the entire response, uncomment the following: // printSafe(originalBytes); // if you want to modify the response, you can either modify the byte array directly, // or first convert it to a string and then modify that, _if_ you know for sure the response is in text form // (otherwise, a string doesn't make sense). jaja");
             byte [] alteredBytes = s2.getBytes();
             log("L: "+originalBytes.length);
             //responseLength = s2.length();
             log("L: "+alteredBytes.length);
             return alteredBytes;
        }*/
        // return the original, unmodified array:
        return originalBytes;
    }

    
    // Constructor, no need to touch this
    public MyProxy(Socket socket, Boolean autoFlush) {
        super(socket, autoFlush);
    }
}

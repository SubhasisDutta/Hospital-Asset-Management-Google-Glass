package glassapptest.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;
import android.util.Log;

public class ServiceCallHandler {
	
	Context activityContext;
	
	public ServiceCallHandler(Context context){
		this.activityContext = context;
	}
	
	public String getServiceToken() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException{
		System.out.println("getservicetoken starts---------------------------");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream caInput = this.activityContext.getAssets().open("certi.cer");
		Certificate ca;
		try {
		    ca = cf.generateCertificate(caInput);
		    String certiHost = "ca=" + ((X509Certificate) ca).getSubjectDN();
		    Log.d("getServiceToken", certiHost);
		} finally {
		    caInput.close();
		}
		
		String keyStoreType = KeyStore.getDefaultType();
		Log.d("getServiceToken", "KeyStroe type is : " + keyStoreType);
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, null);
		keyStore.setCertificateEntry("ca", ca);
		
		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		Log.d("getServiceToken", "trustmanager alogorithm: " + tmfAlgorithm);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);
		
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, tmf.getTrustManagers(), null);
		
		
		URL url = new URL("https://23.96.1.146/api/authenticate");
		HttpsURLConnection urlConnection =
		    (HttpsURLConnection)url.openConnection();
		urlConnection.setHostnameVerifier(new HostnameVerifier(){

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				Log.d("getServiceToken", "Verified host name: " + arg0);
				System.out.println("Verified host name: " + arg0);
				return true;
			}});
		urlConnection.setSSLSocketFactory(context.getSocketFactory());
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Content-Type","application/json");
		
		DataOutputStream output = new DataOutputStream(urlConnection.getOutputStream());  
		String query = "{\"password\":\"icenterapi\"}";
		output.writeBytes(query);
		output.close();
		
		int status = urlConnection.getResponseCode();
		Log.d("", ">>>>>>>>>>>>>>>>>>>Status Code: " + status);
		InputStream in = null;
		if(status > 400){
			in = urlConnection.getErrorStream();
		} else {
			in = urlConnection.getInputStream();
		}	
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        Log.d("", out.toString());
        reader.close();
        
        return out.toString();
	}
	
	public String raiseServiceRequest(String token, String query) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException{
		
		System.out.println("raiseServiceRequest starts---------------------------");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream caInput = this.activityContext.getAssets().open("certi.cer");
		Certificate ca;
		try {
		    ca = cf.generateCertificate(caInput);
		    Log.d("raiseServiceRequest","ca=" + ((X509Certificate) ca).getSubjectDN());
		} finally {
		    caInput.close();
		}
		
		String keyStoreType = KeyStore.getDefaultType();
		Log.d("raiseServiceRequest","KeyStroe type is : " + keyStoreType);
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, null);
		keyStore.setCertificateEntry("ca", ca);
		
		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		Log.d("raiseServiceRequest","trustmanager alogorithm: " + tmfAlgorithm);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);
		
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, tmf.getTrustManagers(), null);
		
		
		URL url = new URL("https://23.96.1.146/api/ServiceRequest");
		HttpsURLConnection urlConnection =
		    (HttpsURLConnection)url.openConnection();
		urlConnection.setHostnameVerifier(new HostnameVerifier(){

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				Log.d("raiseServiceRequest","trustmanager alogorithm: " + "Verified host name: " + arg0);
				return true;
			}});
		urlConnection.setSSLSocketFactory(context.getSocketFactory());
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Content-Type","application/json");
		urlConnection.setRequestProperty("Authorization_Token", "{" + token + "}");
		
		DataOutputStream output = new DataOutputStream(urlConnection.getOutputStream());  
		//String query = "{\"equipmentID\":\"ILINQAMTEST2\",\"modality\":\"CT\",\"facilityID\":\"215DSE\",\"countryCode\":\"US\",\"problemType\":\"System problem\",\"equipmentStatus\":\"CompletelyDown\",\"name\":\"Lastname,firstname\",\"phoneNumber\":\"123456\",\"description\":\"Problem with the monitor\",\"shortDescription\":\"Monitor issue\",\"timePeriod\": \"05,Sep,2013,16,31\",\"serviceCode\": \"A\",\"locale\":\"en_US\",\"requestingApp\":\"icenter\"}";
		System.out.println("query="+query);
		//String query = "{'equipmentID':'ILINQAMTEST2','modality':'CT','facilityID':'215DSE','countryCode':'US','iSOCode':'US','problemType':'System problem','equipmentStatus':'CompletelyDown','name':'Lastname,firstname','phoneNumber':'123456','description':'Problem with the monitor','shortDescription':'Monitor issue','timePeriod': '05,Sep,2013,16,31','serviceCode': 'A','locale':'en_US','requestingApp':'icenter'}";
		output.writeBytes(query);
		output.close();
		
		
		int status = urlConnection.getResponseCode();
		Log.d("raiseServiceRequest","trustmanager alogorithm: " + ">>>>>>>>>>>>>>>>>>>Status Code: " + status);
		InputStream in = null;
		if(status > 400){
			in = urlConnection.getErrorStream();
		} else {
			in = urlConnection.getInputStream();
		}	
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        Log.d("raiseServiceRequest",out.toString());
        reader.close();
        
        return out.toString();
	}
}

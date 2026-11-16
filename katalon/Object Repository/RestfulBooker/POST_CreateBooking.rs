<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Buat booking baru.</description>
   <name>POST_CreateBooking</name>
   <tag></tag>
   <elementGuidId>75ef8a78-ff4d-56fc-a75b-0eacc997eb0f</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;${body}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>d22cd2a8-a645-5e3a-a5c9-b642e967687c</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>b07faf76-fad6-5868-8be9-8a322e6c009c</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${baseUrl}/booking</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.RB_URL</defaultValue>
      <description>Alamat Restful-Booker (dari profil)</description>
      <id>22d26d87-cb48-57b6-9a23-545c6ed1a03f</id>
      <masked>false</masked>
      <name>baseUrl</name>
   </variables>
   <variables>
      <defaultValue>'''{
  &quot;firstname&quot;: &quot;Rivaldi&quot;,
  &quot;lastname&quot;: &quot;QA&quot;,
  &quot;totalprice&quot;: 150,
  &quot;depositpaid&quot;: true,
  &quot;bookingdates&quot;: {
    &quot;checkin&quot;: &quot;2026-12-01&quot;,
    &quot;checkout&quot;: &quot;2026-12-05&quot;
  },
  &quot;additionalneeds&quot;: &quot;Breakfast&quot;
}'''</defaultValue>
      <description>Body JSON booking (diisi keyword Booker dari Map)</description>
      <id>9237d9fc-2d91-5b33-9b9a-3c7616f92aeb</id>
      <masked>false</masked>
      <name>body</name>
   </variables>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>

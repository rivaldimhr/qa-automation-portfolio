<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Ganti seluruh data booking. Butuh token (cookie) atau Basic auth.</description>
   <name>PUT_UpdateBooking</name>
   <tag></tag>
   <elementGuidId>a37da007-cfc9-512b-a470-43968828d076</elementGuidId>
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
      <webElementGuid>24bd36e2-5dc1-5461-b7a8-1aeb44fb7518</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>5139006d-33e0-52c1-83f7-f4e05c27fcd5</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Cookie</name>
      <type>Main</type>
      <value>token=${token}</value>
      <webElementGuid>3214b3c4-50f3-54db-ab61-1cd7814e40bd</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>${authorization}</value>
      <webElementGuid>a212fdb6-915c-510c-bebf-1953b8d359e7</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>PUT</restRequestMethod>
   <restUrl>${baseUrl}/booking/${id}</restUrl>
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
      <id>7de93f13-c288-59ed-9306-f9964eca1129</id>
      <masked>false</masked>
      <name>baseUrl</name>
   </variables>
   <variables>
      <defaultValue>'1'</defaultValue>
      <description>bookingid</description>
      <id>36798812-bb13-56cf-bd28-b5287d3c6ae5</id>
      <masked>false</masked>
      <name>id</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description>Token dari POST /auth; kosong = tanpa cookie token</description>
      <id>ae71d06d-54c0-59a0-b10e-e3d6b00fc3fb</id>
      <masked>false</masked>
      <name>token</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description>Header Authorization, misalnya Basic YWRtaW46cGFzc3dvcmQxMjM=; kosong = tanpa Basic auth</description>
      <id>d0262de9-bd8e-5876-9c46-a2736e949984</id>
      <masked>false</masked>
      <name>authorization</name>
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
      <id>8c64d13e-1029-5848-b835-f481b27fc683</id>
      <masked>false</masked>
      <name>body</name>
   </variables>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>

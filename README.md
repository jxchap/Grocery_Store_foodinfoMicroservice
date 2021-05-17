<strong>Purpose</strong>

  <ul><li>To provide users with an online grocery service that allows users to search for grocery products, checkout, and provide feedback for their shopping experience. </li></ul>
  <strong>Scope</strong>
  <ul><li>The scope of this project includes searching for grocery items, checking out with a 3rd party (Paypal or credit/debit card), reviewing purchase history, and generating      helpdesk items. The internal management of a particular grocery store participating in this service remains outside of the scope of this project. </li></ul>
  <strong>Target Market Segment</strong>
 <ul><li> End users of this service will include those wishing to use a desktop browser in order to search for, and purchase grocery products.</li></ul>


<strong>Features - Customer</strong>
<ul>
 <li> Notification of current sales in effect upon loading home page</li>
 <li> Search for products using either name, description, or category. </li>
 <li> Edit cart</li>
 <li> Paypal checkout</li>
<li>  View purchase history</li>
 <li> Raise questions for staff</li>
</ul>

<strong>Features - Manager</strong>
<ul>
 <li> Create or delete discounts that pertain to a particular category of products.</li>
 <li> Create products or modify product attributes (cost, reorder point, etc.)</li>
<li>  Receive notifications of automatically generated purchase requisition orders. </li>
 <li> Assign customer support requests to employees. </li>
</ul>


<strong>Technologies Used</strong>
<ul>
   <li>Front-End : provided via .jsp files</li>
  <li> HTML </li>
  <li> CSS/Bootstrap</li>
 <li>  jQuery</li>
  <li> Back-End : Spring Boot Microservice Architecture</li>
 <li>  “Hub-and-Spoke” design to reduce communication routes, thereby simplifying troubleshooting and security implementation. The end-user will only ever interact with a front-facing web server (the hub), which then communicates with disparate microservices to perform various operations related to online shopping management.</li>
</ul>

<strong>Back-End Technologies:</strong>
<ul>
  <li>Spring Security</li>
<li>  Dynamic PDF Creation : iText PDF API</li>
<li>  Java Mail : Jakarta Mail API</li>
<li>  MySQL </li>
<li>  Java Persistence API (JPA)</li>
 <li> Hibernate ORM</li>
</ul>



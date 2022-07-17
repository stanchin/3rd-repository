# 3rd-repository

Spring Boot application with REST controller to evaluate statements, like '(14 + 7) * 2 / 13'.

cmd examples:<br>
<b>curl -X GET localhost:8080/api/results</b> to get all calculated results stored in DB<br>
<b>curl -X POST -H "Content-Type:text/plain" localhost:8080/api/calculate -d "(6 + 6) / 0"</b> to perform calculation<br>
<b>curl -X PUT -H "Content-Type:application/json" localhost:8080/api/correct -d "{\"id\":\"62d3ef0dc8d17f2febb710ea\",\"expression\":\"(6 + 6) / 12\",\"result\": null}"</b> to correct object's expression and result<br>

Of course, data in '-d' option is up to you.

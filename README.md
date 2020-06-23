Test Cases in RestAssuredStatusCheckTestCases:


1. Perform get  /api/v1/students without 'Authorization' and check if you get 401.
2. Perform get /api/v1/students with 'Authorization' but invalid username/password.
3. Perform get on /api/v1/students api empty db, and check if 404.
4. Perform post on /api/v1/students api and check if 405.
5. Perform post on /api/v1/student with valid request body and check if 200.
6. Perform get on /api/v1/students and check if 200
7. Perform post with wrong content-type and check if 415
8. Perform put on /api/v1/student with valid request body and check if 200.
9. Perform get on /api/v1/student/{student name} and check if 200.
10. Perform get on /api/v1/student/{invalid-student name} and check if 404.
11. Perform delete on /api/v1/student/{student name} and check if 200.
12. Perform delete on /api/v1/student/{invalid-student name} and check if 404.



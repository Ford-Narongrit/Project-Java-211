6210402402 Narongrit Thammapalo

อธิบาย commit แต่ละสัปดาห์
30/07/2020              -เริ่ม push git ครั้งแรก
13/09/2020-19/09/2020   -เริ่มวางแผน models, ทำหน้าlogin, เพิ่ม maven เข้า project
27/09/2020-30/09/2020   -เริ่ม ทำหน้า worker, ทำหน้า Admin
04/10/2020-11/10/2020   -เริ่ม ทำหน้า register, ทำหน้า resident และ สร้างClass save ข้อมูลลง .csv
12/10/2020-18/10/2020   -เพิ่ม feature อำนวยความสะดวก, การแจ้งเตือน, ธีม program

โครงสร้างไฟล์
|--data   ----> เก็บข้อมูล (.csv file)
|--image  ----> เก็บข้อมูลรูปภาพ
|--src      
    |-main
        |--java 
        |    |--project.neverLand
        |               |--controller  ----> เก็บ controller ที่ใช้ควบคุม หน้าต่างๆ
        |               |--help        ----> เก็บ class ในการ alert 
        |               |--models      ----> เก็บ รูปแบบข้อมูล class
        |               |--services    ----> เก็บ ตัวช่วยในการรวม models กับ controller เข้าด้วยกัน
        |               |--StartProgram.java ----> ตัวเริ่มต้นโปรแกรม
        |
        |--resources
                |--css   ----> เก็บ css file 
                |--image ----> เก็บ รูปภาพพื้นฐานของโปรแกรม
                |--.fxml
                
วิธีการติดตั้งโปรแกรม
่ double click ---> 6210402402-jar

อธิบายวิธีเริ่มต้นใช้งาน

admin -->   username: Admin
            password: 1234
เจ้าหน้าที่ส่วนกลาง  -->   username: worker1
                    password: 1234
ผู้พักอาศัย --> username: resident1
            password: 1234
                   
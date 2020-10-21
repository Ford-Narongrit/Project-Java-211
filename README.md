# 6210402402 Narongrit Thammapalo

# อธิบาย commit แต่ละสัปดาห์
* 30/07/2020              -เริ่ม push git ครั้งแรก

* 13/09/2020-19/09/2020   -เริ่มวางแผน models, ทำหน้าlogin, เพิ่ม maven เข้า project

* 27/09/2020-30/09/2020   -เริ่ม ทำหน้า worker, ทำหน้า Admin

* 04/10/2020-11/10/2020   -เริ่ม ทำหน้า register, ทำหน้า resident และ สร้างClass save ข้อมูลลง .csv

* 12/10/2020-18/10/2020   -เพิ่ม feature อำนวยความสะดวก, การแจ้งเตือน, ธีม program

# โครงสร้างไฟล์
* data ---> เก็บข้อมูลไฟล์ .csv

* images ---> เก็บข้อมูล รูปภาพ

* neverLandProject ---> เก็บตัวเริ่มการทำงานโปรแกรม (.jar สำหรับทดสอบ)

* src
    * main
        * java
            * project.neverLand
                * controller ---> เก็บ class controller ทั้งหมด
                
                * helper ---> เก็บ class ช่วยเหลือในการทำงานต่างๆ
                
                * models ---> เก็บ class models ทั้งหมด
                
                * servise ---> เก็บ class ช่วยเหลือในการแสดงผล
                
                * StartProgram.java ---> main class ใช้เริ่มต้นโปรแกรม
        * resources
            * css ---> เก็บ css ไฟล์ทั้งหมด
            
            * image ---> เก็บไฟล์รูปพื่นฐาน
            
            * .fxml ไฟล์ทั้งหมด

# วิธีการติดตั้งโปรแกรม
่   หลังจาก clone project ไปแล้วให้เข้าไปที่ neverLandProject/6210402402-jar.jar เพื่อเริ่มต้นโปรแกรม

(ต้องมี folder lib และ data เป็นอย่างน้อยถึงจะเริ่มต้นโปรแกรมได้)

# อธิบายวิธีเริ่มต้นใช้งาน
ให้เข้าไปใน neverLandProject/6210402402-jar.jar เพื่อเริ่มต้นโปรแกรม

* Admin 
    * username: Admin
    * password: 1234
* เจ้าหน้าที่ส่วนกลาง
    * username: worker1
    * password: 1234
* ผู้พักอาศัย
    * username: resident1
    * password: 1234
                   
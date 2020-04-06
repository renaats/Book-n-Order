<a href="https://aimeos.org/">
    <img src="https://i.imgur.com/SsMKBfp.png" alt="TuDelft Logo" title="TuDelft" align="right" height="60
    " />
</a>

Book 'n Order
======================
This is the TU Delft app for booking rooms, renting bikes, and ordering food!

## Description of project
Book 'n Order is a Java desktop application that allows students and employees of TU Delft to book study rooms, rent bikes, and purchase food, all in one location. Includes food delivery options, excellent administrator menu and a lot more!

## Group members

| 📸 | Name | Email |
|---|---|---|
| ![](https://i.imgur.com/QOx3q2W.png) | Jorit de Weerdt | j.c.h.p.deweerdt@student.tudelft.nl |
| ![](https://imgur.com/nbPNECF.png) | Liselotte Jongejans | l.j.jongejans@student.tudelft.nl |
| ![](https://i.imgur.com/eK3Wqb0.jpg) | Alto Delia | a.delia@student.tudelft.nl |
| ![](https://i.imgur.com/kBzTAHD.jpg?1) | Mihail Spasov | m.b.spasov@student.tudelft.nl |
| ![]() | Alex De Los Santos | a.delossantossubirats@student.tudelft.nl |
| ![]() | Renāts Jurševskis | r.jursevskis@student.tudelft.nl |

## Setting up the Development Environment
Clone or download the repository. To run the project it is required to have the following added to your project structure of your desired IDE:

[Java JDK 13](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html) & [JavaFX-13](https://openjfx.io/).

It is optional to have a [MYSQL](https://dev.mysql.com/) database. If you [install](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/), Please change the file name of `application.properties.example` to `application.properties` and create a MYSQL database called "OOPP". Fill in the required username and password of your database and the database should be up and running!

If you are interested in contributing to our project, please follow use our [Checkstyle](config/checkstyle/checkstyle.xml) requirements. If you are using [CheckStyle-IDEA](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea), it is then required to use version 8.25. Furthermore, it is essential that [Git LFS](https://git-lfs.github.com/) is installed. No further requirements!

## How to run it
You can run the application directly from the IDE. To run the server side of the application, run `DatabaseApplication`. To run the client side, run `MainApp`. The `DatabaseApplication` will run a database locally and will be reset upon every reboot. Please know that you can set up a MYSQL database that is persistent. See 'Setting up a Development Environment'

Please do know that using the application requires a TuDelft email. Registering with any other email will be declined. If you own a TuDelft email you can register and a verification code will be send your way. After verifying yourself, you can use the app freely.

For testing purposes we have added admin accounts:

Username: staff@tudelft.nl;
Password: 1234;
Role: Staff

Username: admin@tudelft.nl;
Password: 1234;
Role: Admin

Username: building_admin@tudelft.nl;
Password: 1234;
Role: Building Admin

Username: bike_admin@tudelft.nl;
Password: 1234;
Role: Bike Admin

Username: restaurant@tudelft.nl;
Password: 1234;
Role: Restaurant Owner

## How to contribute to the project
All help is always welcome. However, before making changes, please open an issue to discuss your suggested changes.

Contribution requirements:
* 100% method test coverage.
* 85% line coverage.
* Must pass Check Style.
* Muss pass all other tests.

Make sure you run the [Checkstyle](config/checkstyle/checkstyle.xml) before pushing your changes, to reduce the load onto the server.

## Copyright / License
Copyright 2020
# Description #
This is a project that demonstrates use of Spring Boot, Spring MVC (as a REST controller) and Reactor. This project and improvisation is based on the [Building a RESTful Web Service](http://spring.io/guides/gs/rest-service/) guide. 



# Usage #

To build and test 
    
    mvn test

To run it from the command line 

    mvn spring-boot:run

After that your should have application available on port 8080.

You can go to [http://localhost:8080/greeting?name=Evgeniy](http://localhost:8080/greeting?name=Evgeniy)

To see the returned JSON

	{
	  id: 1,
	  content: "Hello, Evgeniy!"
	}

If you try to call [http://localhost:8080/greeting?name=Evgeniy1](http://localhost:8080/greeting?name=Evgeniy1)
you should get back 400 error code.




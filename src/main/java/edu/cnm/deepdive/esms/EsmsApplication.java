package edu.cnm.deepdive.esms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EsmsApplication /*implements CommandLineRunner*/ {

/*  @Autowired
  private ResearcherRepository researcherRepository;

  @Autowired
  private UserRepository userRepository;*/

  public static void main(String[] args) {
    SpringApplication.run(EsmsApplication.class, args);
  }
 /* Set<Role> roles;
  User user;
  Researcher researcher;

  @Override
  public void run(String... args) throws Exception {
    researcherRepository.deleteAll();

    roles = new HashSet<>();
    roles.add(Role.ADMINISTRATOR);
    roles.add(Role.LEAD);

    user = new User();
    user.setOauthKey("jfdkjeiafj123");
    user.setUserName("JoeN");
    user.setFirstName("Joe");
    user.setLastName("Namath");
    user.setConnected(new Date());
    userRepository.save(user);

    researcher = new Researcher();
    researcher.setAccessCardID("joe123");
    researcher.setUser(user);
    researcher.setRoles(roles);

    researcherRepository.save(researcher);

    // Intern
    roles = new HashSet<>();
    roles.add(Role.INTERN);

    user = new User();
    user.setOauthKey("abcdefg456");
    user.setUserName("JohnS");
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setConnected(new Date());
    userRepository.save(user);

    researcher = new Researcher();
    researcher.setAccessCardID("john456");
    researcher.setUser(user);
    researcher.setRoles(roles);

    researcherRepository.save(researcher);

    // Intern
    roles = new HashSet<>();
    roles.add(Role.INTERN);

    user = new User();
    user.setOauthKey("jfdkjeiafj456");
    user.setUserName("JohnS");
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setConnected(new Date());
    userRepository.save(user);

    researcher = new Researcher();
    researcher.setAccessCardID("john456");
    researcher.setUser(user);
    researcher.setRoles(roles);

    researcherRepository.save(researcher);
  }*/
}


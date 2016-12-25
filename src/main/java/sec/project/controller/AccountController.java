package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    private List<Account> accountList;
    private List<Account> accountJpaList;

    @PostConstruct
    public void init() {
        Account account = new Account();
        account.setUsername("Kimi Riki");
        account.setPassword("supersecretpassword");
        accountRepository.save(account);

        Account account1 = new Account();
        account1.setUsername("Mika Haka");
        account1.setPassword("i love F1");
        accountRepository.save(account1);

    }

    @RequestMapping("/account")
    public String sqlInjection(Model model) {
        model.addAttribute("accounts", accountList);
        model.addAttribute("accountsJpa", accountJpaList);
        return "sqlinjection";
    }

    @Transactional
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String getUser(@RequestParam String name) throws SQLException {
        String databaseAddress = "jdbc:h2:mem:testdb";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
        String sqlQuery = "select * from Account where username=" + "'" + name + "'";
        ResultSet resultSet = connection.createStatement().executeQuery(sqlQuery);
        accountList = new ArrayList<>();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            Account account = new Account();
            account.setUsername(username);
            accountList.add(account);
        }

        resultSet.close();
        connection.close();
        return "redirect:/account";
    }

    @Transactional
    @RequestMapping(value = "/account_jpa", method = RequestMethod.POST)
    public String getUserJpa(@RequestParam String name) throws SQLException {
        Account account = accountRepository.findByUsername(name);

        if (account == null) return "error";

        accountJpaList = new ArrayList<>();
        accountJpaList.add(account);
        return "redirect:/account";
    }
}




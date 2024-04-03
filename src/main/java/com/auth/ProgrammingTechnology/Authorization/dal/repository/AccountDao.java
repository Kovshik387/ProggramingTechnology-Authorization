package com.auth.ProgrammingTechnology.Authorization.dal.repository;

import com.auth.ProgrammingTechnology.Authorization.dal.Context;
import com.auth.ProgrammingTechnology.Authorization.dal.infrastructure.IAccountDao;
import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Component
@Slf4j
public class AccountDao implements IAccountDao {
    private final String hash;
    public AccountDao(@Value("${password.hash}") int salt) {
        this.hash = BCrypt.gensalt(salt);
    }

    @Override
    public void add(Account account) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        var pass_hash = BCrypt.hashpw(account.getPasswordHash(),hash);
        account.setPasswordHash(pass_hash);

        var state = connection.prepareStatement("insert into accounts(name, email, password_hash, refresh_token, role_type) values (?,?,?,?,?)");
        state.setString(1, account.getName());
        state.setString(2, account.getEmail());
        state.setString(3, account.getPasswordHash());
        state.setString(4, account.getRefreshToken());
        state.setString(5, account.getRoleType());

        state.executeUpdate();
    }

    @Override
    public void update(Account account) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("update accounts set name = ?, email = ?, role_type = ? where account_id = ?");
        state.setString(1, account.getName());
        state.setString(2, account.getEmail());
        state.setString(3, account.getRoleType());
        state.setString(4, String.valueOf(account.getId()));

        state.executeUpdate();
    }

    @Override
    public Account get(int id) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts where account_id = ?");
        state.setInt(1, id);
        var result = state.executeQuery();

        Account account = null;
        while (result.next()){
            account = new Account(result.getInt("account_id"),result.getString("name"),
                    result.getString("email"),result.getString("password_hash"),result.getString("refresh_token"),result.getString("role_type"));
        }
        return account;
    }

    @Override
    public Collection<Account> getAll() throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts");
        var result = state.executeQuery();

        Collection<Account> list = new ArrayList<>();
        while (result.next()){
            list.add(new Account(result.getInt("account_id"),result.getString("name"),
                    result.getString("email"),result.getString("password_hash"),
                    result.getString("refresh_token"),result.getString("role_type"))
            );
        }
        return list;
    }

    @Override
    public Account getByLogin(SignInRequest model) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts where email = ?");
        state.setString(1,model.getEmail());

        var result = state.executeQuery();

        Account account = null;
        while (result.next()){
            account = new Account(result.getInt("account_id"),result.getString("name"),
                    result.getString("email"),result.getString("password_hash"),result.getString("refresh_token"),result.getString("role_type"));
        }

        if (!Objects.isNull(account)){
            if (BCrypt.checkpw(model.getPassword(), account.getPasswordHash())){
                return account;
            }
        }

        return account;
    }

    @Override
    public int accountExist(String email) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select account_id from accounts where email = ?");
        state.setString(1,email);
        var result = state.executeQuery();

        var id = 0;
        while (result.next()){
            id = result.getInt("account_id");
        }
        return id;
    }

    @Override
    public void delete(int id) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("delete from accounts where account_id = ?");
        state.setInt(1, id);

        state.executeUpdate();
    }
}

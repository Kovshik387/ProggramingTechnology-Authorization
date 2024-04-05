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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

        var state = connection.prepareStatement("insert into accounts (first_name, surname, last_name, email, password_hash, created_at, updated_at, role) values (?,?,?,?,?,?,?,?)");
        state.setString(1, account.getFirstName());
        state.setString(2, account.getSurname());
        state.setString(3, account.getLastName());
        state.setString(4, account.getEmail());
        state.setString(5, account.getPasswordHash());
        state.setTimestamp(6, new Timestamp(new Date().getTime()));
        state.setTimestamp(7,new Timestamp(new Date().getTime()));
        state.setInt(8,account.getRole());

        state.executeUpdate();
    }

    @Override
    public void update(Account account) throws SQLException {
        var connection = Context.createDataSource().getConnection();

//        PreparedStatement state = connection.prepareStatement("update accounts set name = ?, email = ?, role_type = ? where account_id = ?");
//        state.setString(1, account.getName());
//        state.setString(2, account.getEmail());
//        state.setString(3, account.getRoleType());
//        state.setString(4, String.valueOf(account.getId()));
//
//        state.executeUpdate();
    }

    @Override
    public Account get(int id) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts where id = ?");
        state.setInt(1, id);
        var result = state.executeQuery();

        Account account = null;
        while (result.next()){
            account = new Account(
                    result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("surname"),
                    result.getString("last_name"),
                    result.getString("email"),
                    result.getString("password_hash"),
                    result.getDate("created_at"),
                    result.getDate("updated_at"),
                    result.getInt("role"),
                    result.getInt("activity_status"),
                    result.getBoolean("is_deleted"),
                    result.getInt("current_event")
            );
        }
        return account;
    }

    @Override
    public Collection<Account> getAll() throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts");
        var result = state.executeQuery();

        Collection<Account> list = new ArrayList<>();
//        while (result.next()){
//            list.add(new Account(result.getInt("id"),result.getString("name"),
//                    result.getString("email"),
//                    result.getString("password_hash"),
//                    //result.getString("refresh_token")
//                    result.getString("role_type"))
//            );
//        }
        return list;
    }

    @Override
    public Account getByLogin(String login) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts where email = ?");
        state.setString(1,login);
        var result = state.executeQuery();

        Account account = null;
        while (result.next()){
            account = new Account(
                    result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("surname"),
                    result.getString("last_name"),
                    result.getString("email"),
                    result.getString("password_hash"),
                    result.getDate("created_at"),
                    result.getDate("updated_at"),
                    result.getInt("role"),
                    result.getInt("activity_status"),
                    result.getBoolean("is_deleted"),
                    result.getInt("current_event")
            );
        }
        return account;
    }

    @Override
    public Account getByCredits(SignInRequest model) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select * from accounts where email = ?");
        state.setString(1,model.getEmail());
        var result = state.executeQuery();

        Account account = null;
        while (result.next()){
            account = new Account(
                    result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("surname"),
                    result.getString("last_name"),
                    result.getString("email"),
                    result.getString("password_hash"),
                    result.getDate("created_at"),
                    result.getDate("updated_at"),
                    result.getInt("role"),
                    result.getInt("activity_status"),
                    result.getBoolean("is_deleted"),
                    result.getInt("current_event")
            );
        }

        if (!Objects.isNull(account)){
            if (BCrypt.checkpw(model.getPassword(), account.getPasswordHash())){
                return account;
            }
        }

        return null;
    }

    @Override
    public int accountExist(String email) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("select id from accounts where email = ?");
        state.setString(1,email);
        var result = state.executeQuery();

        var id = 0;
        while (result.next()){
            id = result.getInt("id");
        }
        return id;
    }

    @Override
    public void delete(int id) throws SQLException {
        var connection = Context.createDataSource().getConnection();

        PreparedStatement state = connection.prepareStatement("delete from accounts where id = ?");
        state.setInt(1, id);

        state.executeUpdate();
    }
}

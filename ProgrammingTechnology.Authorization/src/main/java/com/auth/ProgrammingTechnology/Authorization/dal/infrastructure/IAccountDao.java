package com.auth.ProgrammingTechnology.Authorization.dal.infrastructure;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.request.SignInDto;

import java.sql.SQLException;
import java.util.Collection;

public interface IAccountDao {
    public void add(Account account) throws SQLException;
    public void update(Account account) throws SQLException;
    public Account get(int id) throws SQLException;
    public Collection<Account> getAll() throws SQLException;
    public Account getByLogin(String login) throws SQLException;
    public Account getByCredits(SignInDto account) throws SQLException;
    public int accountExist(String email) throws SQLException;
    public void delete(int id) throws SQLException;
}

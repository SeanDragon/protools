package sd.data.json;

import com.google.common.base.MoreObjects;
import org.junit.Test;
import pro.tools.data.decimal.Decimal;
import pro.tools.data.text.ToolJson;
import pro.tools.data.text.json.TypeBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeanDragon Create By 2017-06-27 15:14
 */
public class Test_20170627 {
    @Test
    public void test1() {
        Account account = new Account();
        account.setMoney(Decimal.instance(1500.25))
                .setAccountId("account1");

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        User user = new User();
        user.setUserName("userName1")
                .setUserId("user1")
                .setAccounts(accounts);

        String s = ToolJson.modelToJson(user);
        System.out.println(s);

        User user1 = ToolJson.jsonToModel(s, User.class);

        System.out.println(user1);
    }

    @Test
    public void test2() {

        Account account = new Account();
        account.setMoney(Decimal.instance(1500.25))
                .setAccountId("account1");

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        User<Account> user = new User<>();
        user.setUserName("userName1")
                .setUserId("user1")
                .setAccounts(accounts);


        JsonResult<User> jsonResult = new JsonResult<>();
        jsonResult.setHaveError(true);
        jsonResult.setCode("666");
        jsonResult.setData(user);

        String s = ToolJson.modelToJson(jsonResult);

        System.out.println(s);

        //JsonResult jsonResult1 = ToolJson.jsonToModel(s, JsonResult.class);
        //
        //System.out.println(jsonResult1.getData().getClass());

        //JsonResult<User<Account>>

        //JsonResult<User<Account>>

        TypeBuilder.newInstance(JsonResult.class);

        Type type = TypeBuilder.newInstance(JsonResult.class)
                .beginSubType(User.class)
                .beginSubType(Account.class)
                .endSubType()
                .endSubType()
                .build();

        JsonResult<User<Account>> jsonResult1 = ToolJson.jsonToAny(s, type);

        System.out.println(jsonResult1.getData().getClass());

        User data = jsonResult1.getData();
        System.out.println(data.getAccounts().get(0).getClass());
    }


}

class User<T> {
    private String userId;
    private String userName;
    private List<T> accounts;

    public List<T> getAccounts() {
        return accounts;
    }

    public User setAccounts(List<T> accounts) {
        this.accounts = accounts;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("accounts", accounts)
                .toString();
    }
}

class Account {
    private String accountId;
    private Decimal money;

    public String getAccountId() {
        return accountId;
    }

    public Account setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Decimal getMoney() {
        return money;
    }

    public Account setMoney(Decimal money) {
        this.money = money;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("money", money)
                .toString();
    }
}

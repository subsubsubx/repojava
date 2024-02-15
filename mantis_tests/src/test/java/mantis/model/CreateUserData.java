package mantis.model;


/*
* ## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**username** | **String** |  |  [optional]
**password** | **String** |  |  [optional]
**realName** | **String** |  |  [optional]
**email** | **String** |  |  [optional]
**enabled** | **Boolean** |  |  [optional]
**_protected** | **Boolean** |  |  [optional]
**accessLevel** | [**AccessLevel**](AccessLevel.md) |  |  [optional]

*
* */

public record CreateUserData(String username, String password, String realName, String email,
                             Boolean enabled, Boolean _protected, String accessLevel) {


    public CreateUserData() {
        this("", "", "", "", false, false, null);
    }

    public CreateUserData withUsername(String username) {
        return new CreateUserData(username, this.password, this.realName, this.email, this.enabled, this._protected
                , this.accessLevel);
    }

    public CreateUserData withPassword(String password) {
        return new CreateUserData(this.username, password, this.realName, this.email, this.enabled, this._protected
                , this.accessLevel);
    }

    public CreateUserData withRealName(String realName) {
        return new CreateUserData(this.username, this.password, realName, this.email, this.enabled, this._protected
                , this.accessLevel);
    }

    public CreateUserData withEmail(String email) {
        return new CreateUserData(this.username, this.password, this.realName, email, this.enabled, this._protected
                , this.accessLevel);
    }

    public CreateUserData withEnabled(Boolean enabled) {
        return new CreateUserData(this.username, this.password, this.realName, this.email, enabled, this._protected
                , this.accessLevel);
    }

    public CreateUserData withProtected(Boolean _protected) {
        return new CreateUserData(this.username, this.password, this.realName, this.email, this.enabled, _protected
                , this.accessLevel);
    }

    public CreateUserData withAccessLevel(String accessLevel) {
        return new CreateUserData(this.username, this.password, this.realName, this.email, this.enabled, this._protected
                , accessLevel);
    }

}

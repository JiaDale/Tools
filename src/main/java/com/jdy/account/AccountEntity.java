package com.jdy.account;

import com.jdy.annotation.Column;
import com.jdy.annotation.Table;
import com.jdy.entity.BaseEntity;

import java.util.Date;

@Table("Frame_User")
public class AccountEntity extends BaseEntity {

    @Column("UserAge")
    public int getAge() {
        return getInt("UserAge");
    }

    @Column("UserName")
    public void setName(String name){
        super.set("UserName", name);
    }

    @Column("UserName")
    public String getName(){
        return super.get("UserName", "");
    }

    @Column("UserAge")
    public void setAge(int age) {
        super.set("UserAge", age);
    }

    @Column("Password")
    public String getPassword(){
        return super.getStr("Password");
    }

    @Column("CreatedTime")
    public Date getCreatedTime(){
        return super.getDate("CreatedTime");
    }

    @Column("CreateAddress")
    public String getCreateAddress(){
        return super.getStr("CreateAddress");
    }

}

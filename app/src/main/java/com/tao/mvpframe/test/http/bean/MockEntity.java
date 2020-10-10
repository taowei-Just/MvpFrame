package com.tao.mvpframe.test.http.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MockEntity {
    List<Project> projects;

    public static class Project {

        String address;
        @SerializedName("boolean")
        boolean boolea;
        String email;
        String name;
        int number;
        String string;
        String url;

        @Override
        public String toString() {
            return "Project{" +
                    "address='" + address + '\'' +
                    ", boolea=" + boolea +
                    ", email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", number=" + number +
                    ", string='" + string + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class Obj {

        @SerializedName("320000")
        String name1;

        @Override
        public String toString() {
            return "Obj{" +
                    "name1='" + name1 + '\'' +
                    ", name2='" + name2 + '\'' +
                    '}';
        }

        @SerializedName("330000")
        String name2;

    }

    @Override
    public String toString() {
        return "MockEntity{" +
                "projects=" + projects +
                '}';
    }
}

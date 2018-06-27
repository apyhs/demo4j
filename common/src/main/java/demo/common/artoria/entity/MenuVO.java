package demo.common.artoria.entity;

import java.util.List;

public class MenuVO {
    private Integer id;
    private String name;
    private String code;
    private String parentCode;
    private List<Menu> sonMenuList;
    private String module;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<Menu> getSonMenuList() {
        return sonMenuList;
    }

    public void setSonMenuList(List<Menu> sonMenuList) {
        this.sonMenuList = sonMenuList;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}

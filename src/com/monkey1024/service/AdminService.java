
package com.monkey1024.service;

import com.monkey1024.bean.Admin;


public interface AdminService {

    Admin get(String name);

    void save(Admin admin);

}

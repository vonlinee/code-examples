package org.springboot.sample.security;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

public class MySessionDao extends AbstractSessionDAO {

    @Override
    public void update(Session session) throws UnknownSessionException {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Session session) {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<Session>

    getActiveSessions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Serializable doCreate(Session session) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        // TODO Auto-generated method stub
        return null;
    }

}

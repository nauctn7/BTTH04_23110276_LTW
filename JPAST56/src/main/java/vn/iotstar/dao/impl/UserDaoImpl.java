package vn.iotstar.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfigs;
import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;

public class UserDaoImpl implements UserDao {

    @Override
    public User findByUsername(String username) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            TypedQuery<User> q = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :un AND u.active = true",
                User.class
            );
            q.setParameter("un", username);
            // Dùng getResultList() an toàn với mọi provider
            List<User> list = q.setMaxResults(1).getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public User findById(int id) {                  // <— CHÍNH LÀ CHỖ BỊ LỖI, ĐÃ IMPLEMENT
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            return em.find(User.class, id);         // cách chuẩn lấy theo khóa chính
        } finally {
            em.close();
        }
    }

    @Override
    public void create(User u) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(u);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

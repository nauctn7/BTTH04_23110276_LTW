package vn.iotstar.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfigs;
import vn.iotstar.dao.CategoryDao;
import vn.iotstar.entity.Category;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public void create(Category category) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(category);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); throw e;
        } finally { em.close(); }
    }

    @Override
    public void update(Category category) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(category);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); throw e;
        } finally { em.close(); }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category c = em.find(Category.class, id);
            if (c != null) em.remove(c);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); throw e;
        } finally { em.close(); }
    }

    @Override
    public List<Category> findAll() {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            // nạp luôn owner để JSP không bị lazy
            return em.createQuery(
                "SELECT c FROM Category c JOIN FETCH c.owner ORDER BY c.id DESC",
                Category.class
            ).getResultList();
        } finally { em.close(); }
    }

    @Override
    public List<Category> findByOwnerId(int ownerId) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM Category c JOIN FETCH c.owner WHERE c.owner.id = :oid ORDER BY c.id DESC",
                Category.class
            ).setParameter("oid", ownerId).getResultList();
        } finally { em.close(); }
    }

    @Override
    public Category findById(int id) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            // khi vào trang edit, cũng nạp owner luôn
            List<Category> list = em.createQuery(
                "SELECT c FROM Category c JOIN FETCH c.owner WHERE c.id = :id",
                Category.class
            ).setParameter("id", id).setMaxResults(1).getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally { em.close(); }
    }

}

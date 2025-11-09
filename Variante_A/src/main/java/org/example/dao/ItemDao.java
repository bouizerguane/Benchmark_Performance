package org.example.dao;

import org.example.model.Item;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.List;

public class ItemDao implements Idao<Item> {

    @Override
    public boolean save(Item entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Item> findAll(int page, int size) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Item", Item.class)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        }
    }

    @Override
    public Item findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Item.class, id);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Item item = session.find(Item.class, id);
            if (item != null) {
                session.remove(item);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Item item) {
        if (item == null || item.getId() == null) {
            return false;
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Item existingItem = session.find(Item.class, item.getId());
            if (existingItem == null) {
                return false;
            }

            existingItem.setSku(item.getSku());
            existingItem.setName(item.getName());
            existingItem.setPrice(item.getPrice());
            existingItem.setStock(item.getStock());
            existingItem.setCategory(item.getCategory());
            existingItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            session.merge(existingItem);

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> findByCategoryId(Long categoryId, int page, int size) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Item i WHERE i.category.id = :categoryId", Item.class)
                    .setParameter("categoryId", categoryId)
                    .setFirstResult(page * size)
                    .setMaxResults(size)
                    .getResultList();
        }
    }

}

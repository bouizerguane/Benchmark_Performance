package org.example.dao;

import org.example.model.Category;
import org.example.model.Item;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryDao implements Idao<Category> {

    @Override
    public boolean save(Category entity) {
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
    public List<Category> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Category", Category.class).getResultList();
        }
    }

    @Override
    public Category findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Category.class, id);
        }

    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Category category = session.find(Category.class, id);
            if (category != null) {
                session.remove(category);
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
    public boolean update(Category category) {
        if (category == null || category.getId() == null) {
            return false;
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Category existingCategory = session.find(Category.class, category.getId());
            if (existingCategory == null) {
                return false;
            }
            existingCategory.setCode(category.getCode());
            existingCategory.setName(category.getName());
            existingCategory.setUpdatedAt(LocalDateTime.now());

            session.merge(existingCategory);

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> findByCategoryId(Long categoryId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Item i WHERE i.category.id = :categoryId", Item.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
        }
    }
}

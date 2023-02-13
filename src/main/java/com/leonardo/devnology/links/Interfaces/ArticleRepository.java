package com.leonardo.devnology.links.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.devnology.links.Entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
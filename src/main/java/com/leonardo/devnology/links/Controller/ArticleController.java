package com.leonardo.devnology.links.Controller;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.devnology.links.Entity.Article;
import com.leonardo.devnology.links.Interfaces.ArticleRepository;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleRepository repository;

    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/api/links")
    public ResponseEntity<Object> addLink(@RequestBody Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO links (url, title) VALUES (?,?)",
                    new String[] { "id" });
            ps.setString(1, article.getUrl());
            ps.setString(2, article.getTitle());
            return ps;
        }, keyHolder);
        article.setId(keyHolder.getKey().longValue());
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @GetMapping("/api/links")
    public List<Article> getLinks() {
        return jdbcTemplate.query("SELECT * FROM links",
                (rs, rowNum) -> new Article(rs.getLong("id"), rs.getString("title"), rs.getString("url")));
    }

    @PutMapping("/api/links/{id}")
    public ResponseEntity<Object> updateLink(@PathVariable Long id, @RequestBody Article article) {
        int result = jdbcTemplate.update("UPDATE links SET url = ?, title = ? WHERE id = ?",
                article.getUrl(), article.getTitle(),id);
        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        article.setId(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<Object> deleteLink(@PathVariable long id) {
        jdbcTemplate.update("DELETE FROM links WHERE id = ?", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

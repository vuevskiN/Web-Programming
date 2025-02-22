package mk.ukim.finki.wp.september2021.repository;

import mk.ukim.finki.wp.september2021.model.News;
import mk.ukim.finki.wp.september2021.model.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository  extends JpaRepository<News, Long> {

    List<News> findAllByPriceLessThan(double price);
    List<News> findAllByType(NewsType type);
    List<News> findAllByPriceLessThanAndType(double price, NewsType type);
}

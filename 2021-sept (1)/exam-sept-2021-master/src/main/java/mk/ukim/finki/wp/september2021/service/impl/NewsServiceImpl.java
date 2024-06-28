
package mk.ukim.finki.wp.september2021.service.impl;

import mk.ukim.finki.wp.september2021.model.News;
import mk.ukim.finki.wp.september2021.model.NewsCategory;
import mk.ukim.finki.wp.september2021.model.NewsType;
import mk.ukim.finki.wp.september2021.model.exceptions.InvalidNewsIdException;
import mk.ukim.finki.wp.september2021.repository.NewsRepository;
import mk.ukim.finki.wp.september2021.service.NewsCategoryService;
import mk.ukim.finki.wp.september2021.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsCategoryService newsCategoryService;
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsCategoryService newsCategoryService, NewsRepository newsRepository) {
        this.newsCategoryService = newsCategoryService;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> listAllNews() {
        return this.newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return this.newsRepository.findById(id).orElseThrow(InvalidNewsIdException::new);
    }

    @Override
    public News create(String name, String description, Double price, NewsType type, Long category) {
        NewsCategory newsCategory=this.newsCategoryService.findById(category);
        News news=new News(name,description,price,type,newsCategory);
        return this.newsRepository.save(news);
    }

    @Override
    public News update(Long id, String name, String description, Double price, NewsType type, Long category) {
        NewsCategory newsCategory=this.newsCategoryService.findById(category);
        News news=findById(id);
        news.setName(name);
        news.setDescription(description);
        news.setPrice(price);
        news.setType(type);
        news.setCategory(newsCategory);
        return this.newsRepository.save(news);
    }

    @Override
    public News delete(Long id) {
        News news=findById(id);
        this.newsRepository.delete(news);
        return news;
    }

    @Override
    public News like(Long id) {
        News news=findById(id);
        news.setLikes(news.getLikes()+1);
        return this.newsRepository.save(news);
    }

    @Override
    public List<News> listNewsWithPriceLessThanAndType(Double price, NewsType type) {
        if(price==null && type==null){
            return listAllNews();
        }else if(price!=null && type!=null){
            return this.newsRepository.findAllByPriceLessThanAndType(price,type);
        }else if(price!=null){
            return this.newsRepository.findAllByPriceLessThan(price);
        }else{
            return this.newsRepository.findAllByType(type);
        }
    }
}

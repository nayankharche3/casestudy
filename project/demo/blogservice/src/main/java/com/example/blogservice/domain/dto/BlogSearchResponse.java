package com.example.blogservice.domain.dto;

import lombok.Getter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class BlogSearchResponse {
    private final String category;
    private final Instant fromDate;
    private final Instant toDate;
    private final int count;
    private final List<BlogSearchItem> items;

    private BlogSearchResponse(Builder builder) {
        this.category = builder.category;
        this.fromDate = builder.fromDate;
        this.toDate = builder.toDate;
        this.items = Collections.unmodifiableList(builder.items);
        this.count = this.items.size();
    }

    public static class Builder {
        private String category;
        private Instant fromDate;
        private Instant toDate;
        private List<BlogSearchItem> items = new ArrayList<>();

        public Builder category(String category) { this.category = category; return this; }
        public Builder range(Instant from, Instant to) { this.fromDate = from; this.toDate = to; return this; }
        public Builder addItem(BlogSearchItem item) { this.items.add(item); return this; }
        public Builder addItems(Collection<BlogSearchItem> items) { this.items.addAll(items); return this; }
        public BlogSearchResponse build() { return new BlogSearchResponse(this); }
    }
}
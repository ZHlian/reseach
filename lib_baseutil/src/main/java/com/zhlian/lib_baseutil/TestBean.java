package com.zhlian.lib_baseutil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 64553 on 2018-11-12.
 * Power By ChangnAutoMobile RCLink Team
 */
@Entity(indexes = {
        @Index(value = "id DESC")
})
public class TestBean {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String text;

@Generated(hash = 1563096333)
public TestBean(Long id, @NotNull String text) {
    this.id = id;
    this.text = text;
}

@Generated(hash = 2087637710)
public TestBean() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getText() {
    return this.text;
}

public void setText(String text) {
    this.text = text;
}
}

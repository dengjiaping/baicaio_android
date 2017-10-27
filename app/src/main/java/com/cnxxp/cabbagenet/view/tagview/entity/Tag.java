/*
 * Copyright 2014 Namito.S
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cnxxp.cabbagenet.view.tagview.entity;

import java.util.Map;

/**
 * Tag Class
 */
public class Tag {

    private int id;
    private String text;
    private Map<?,?> attrs;

    public Tag(int id, String text){
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<?, ?> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<?, ?> attrs) {
        this.attrs = attrs;
    }
}

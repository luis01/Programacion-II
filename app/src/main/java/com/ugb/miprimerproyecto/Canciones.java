package com.ugb.miprimerproyecto;

public class Canciones {
    String title;
    Long id;
        public Canciones(String title, Long id) {
            this.title = title;
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
}

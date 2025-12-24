package com.lh2z.domain;

import jakarta.persistence.*;

@Entity
public class Country {
  @Id
  @Column(length = 3)
  private String id;

  @Column(nullable = false)
  private String name;

  private String region;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getRegion() { return region; }
  public void setRegion(String region) { this.region = region; }
}




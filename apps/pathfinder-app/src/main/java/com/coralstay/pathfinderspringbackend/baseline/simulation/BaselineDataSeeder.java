package com.coralstay.pathfinderspringbackend.baseline.simulation;

import org.springframework.stereotype.Component;

@Component
public class BaselineDataSeeder implements DataSeeder {

  @Override
  public void seed() {
    System.out.println("Seeding baseline data...");
  }
}

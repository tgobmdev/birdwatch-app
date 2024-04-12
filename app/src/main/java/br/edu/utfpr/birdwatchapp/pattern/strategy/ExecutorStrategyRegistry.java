package br.edu.utfpr.birdwatchapp.pattern.strategy;

import java.util.HashMap;
import java.util.Map;

public interface ExecutorStrategyRegistry {

  Map<Integer, ExecutorStrategy> strategyMap = new HashMap<>();

  static void register(int menuItemId, ExecutorStrategy strategy) {
    if (strategy == null) {
      throw new IllegalArgumentException("The executor cannot be null.");
    }

    if (!strategyMap.containsKey(menuItemId)) {
      strategyMap.put(menuItemId, strategy);
    }
  }

  static ExecutorStrategy getExecutor(int menuItemId) {
    return strategyMap.get(menuItemId);
  }
}
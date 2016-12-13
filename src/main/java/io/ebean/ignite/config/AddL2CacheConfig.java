package io.ebean.ignite.config;

import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Adds configuration together.
 */
class AddL2CacheConfig {

  /**
   * Add the apply configuration to base and return the combined configuration.
   */
  static L2CacheConfig add(L2CacheConfig base, L2CacheConfig apply) {

    L2CacheConfig newConfig = new L2CacheConfig();
    apply(newConfig, base);
    if (apply != null) {
      apply(newConfig, apply);
    }
    return newConfig;
  }

  private static void apply(L2CacheConfig to, L2CacheConfig from) {

    if (from.atomicityMode != null) {
      to.atomicityMode = from.atomicityMode;
    }
    if (from.atomicWriteOrderMode != null) {
      to.atomicWriteOrderMode = from.atomicWriteOrderMode;
    }
    if (from.backups != null) {
      to.backups = from.backups;
    }
    if (from.cpOnRead != null) {
      to.cpOnRead = from.cpOnRead;
    }
    if (from.cacheMode != null) {
      to.cacheMode = from.cacheMode;
    }
    if (from.dfltLockTimeout != null) {
      to.dfltLockTimeout = from.dfltLockTimeout;
    }
    if (from.eagerTtl != null) {
      to.eagerTtl = from.eagerTtl;
    }
    if (from.evictSync != null) {
      to.evictSync = from.evictSync;
    }
    if (from.evictKeyBufSize != null) {
      to.evictKeyBufSize = from.evictKeyBufSize;
    }
    if (from.evictMaxOverflowRatio != null) {
      to.evictMaxOverflowRatio = from.evictMaxOverflowRatio;
    }
    if (from.evictSyncConcurrencyLvl != null) {
      to.evictSyncConcurrencyLvl = from.evictSyncConcurrencyLvl;
    }
    if (from.evictSyncTimeout != null) {
      to.evictSyncTimeout = from.evictSyncTimeout;
    }
    if (from.invalidate != null) {
      to.invalidate = from.invalidate;
    }
    if (from.loadPrevVal != null) {
      to.loadPrevVal = from.loadPrevVal;
    }
    if (from.longQryWarnTimeout != null) {
      to.longQryWarnTimeout = from.longQryWarnTimeout;
    }
    if (from.maxConcurrentAsyncOps != null) {
      to.maxConcurrentAsyncOps = from.maxConcurrentAsyncOps;
    }
    if (from.memMode != null) {
      to.memMode = from.memMode;
    }
    if (from.name != null) {
      to.name = from.name;
    }
    if (from.nearSize != null) {
      to.nearSize = from.nearSize;
    }
    if (from.offHeapMaxMem != null) {
      to.offHeapMaxMem = from.offHeapMaxMem;
    }
    if (from.readFromBackup != null) {
      to.readFromBackup = from.readFromBackup;
    }
    if (from.rebalanceBatchesPrefetchCount != null) {
      to.rebalanceBatchesPrefetchCount = from.rebalanceBatchesPrefetchCount;
    }
    if (from.rebalanceBatchSize != null) {
      to.rebalanceBatchSize = from.rebalanceBatchSize;
    }
    if (from.rebalanceDelay != null) {
      to.rebalanceDelay = from.rebalanceDelay;
    }
    if (from.rebalanceMode != null) {
      to.rebalanceMode = from.rebalanceMode;
    }
    if (from.rebalanceOrder != null) {
      to.rebalanceOrder = from.rebalanceOrder;
    }
    if (from.rebalanceThrottle != null) {
      to.rebalanceThrottle = from.rebalanceThrottle;
    }
    if (from.rebalanceTimeout != null) {
      to.rebalanceTimeout = from.rebalanceTimeout;
    }
    if (from.snapshotableIdx != null) {
      to.snapshotableIdx = from.snapshotableIdx;
    }
    if (from.storeKeepBinary != null) {
      to.storeKeepBinary = from.storeKeepBinary;
    }
    if (from.swapEnabled != null) {
      to.swapEnabled = from.swapEnabled;
    }
    if (from.tmLookupClsName != null) {
      to.tmLookupClsName = from.tmLookupClsName;
    }
    if (from.writeBehindEnabled != null) {
      to.writeBehindEnabled = from.writeBehindEnabled;
    }
    if (from.writeBehindBatchSize != null) {
      to.writeBehindBatchSize = from.writeBehindBatchSize;
    }
    if (from.writeBehindFlushFreq != null) {
      to.writeBehindFlushFreq = from.writeBehindFlushFreq;
    }
    if (from.writeBehindFlushSize != null) {
      to.writeBehindFlushSize = from.writeBehindFlushSize;
    }
    if (from.writeBehindFlushThreadCnt != null) {
      to.writeBehindFlushThreadCnt = from.writeBehindFlushThreadCnt;
    }
    if (from.writeSync != null) {
      to.writeSync = from.writeSync;
    }
  }

  static void apply(CacheConfiguration to, L2CacheConfig from) {

    if (from.atomicityMode != null) {
      to.setAtomicityMode(from.atomicityMode);
    }
    if (from.atomicWriteOrderMode != null) {
      to.setAtomicWriteOrderMode(from.atomicWriteOrderMode);
    }
    if (from.backups != null) {
      to.setBackups(from.backups);
    }
    if (from.cpOnRead != null) {
      to.setCopyOnRead(from.cpOnRead);
    }
    if (from.cacheMode != null) {
      to.setCacheMode(from.cacheMode);
    }
    if (from.dfltLockTimeout != null) {
      to.setDefaultLockTimeout(from.dfltLockTimeout);
    }
    if (from.eagerTtl != null) {
      to.setEagerTtl(from.eagerTtl);
    }
    if (from.evictSync != null) {
      to.setEvictSynchronized(from.evictSync);
    }
    if (from.evictKeyBufSize != null) {
      to.setEvictSynchronizedKeyBufferSize(from.evictKeyBufSize);
    }
    if (from.evictMaxOverflowRatio != null) {
      to.setEvictMaxOverflowRatio(from.evictMaxOverflowRatio);
    }
    if (from.evictSyncConcurrencyLvl != null) {
      to.setEvictSynchronizedConcurrencyLevel(from.evictSyncConcurrencyLvl);
    }
    if (from.evictSyncTimeout != null) {
      to.setEvictSynchronizedTimeout(from.evictSyncTimeout);
    }
    if (from.invalidate != null) {
      to.setInvalidate(from.invalidate);
    }
    if (from.loadPrevVal != null) {
      to.setLoadPreviousValue(from.loadPrevVal);
    }
    if (from.longQryWarnTimeout != null) {
      to.setLongQueryWarningTimeout(from.longQryWarnTimeout);
    }
    if (from.maxConcurrentAsyncOps != null) {
      to.setMaxConcurrentAsyncOperations(from.maxConcurrentAsyncOps);
    }
    if (from.memMode != null) {
      to.setMemoryMode(from.memMode);
    }
    if (from.name != null) {
      to.setName(from.name);
    }
    if (from.offHeapMaxMem != null) {
      to.setOffHeapMaxMemory(from.offHeapMaxMem);
    }
    if (from.readFromBackup != null) {
      to.setReadFromBackup(from.readFromBackup);
    }
    if (from.rebalanceBatchesPrefetchCount != null) {
      to.setRebalanceBatchesPrefetchCount(from.rebalanceBatchesPrefetchCount);
    }
    if (from.rebalanceBatchSize != null) {
      to.setRebalanceBatchSize(from.rebalanceBatchSize);
    }
    if (from.rebalanceDelay != null) {
      to.setRebalanceDelay(from.rebalanceDelay);
    }
    if (from.rebalanceMode != null) {
      to.setRebalanceMode(from.rebalanceMode);
    }
    if (from.rebalanceOrder != null) {
      to.setRebalanceOrder(from.rebalanceOrder);
    }
    if (from.rebalanceThrottle != null) {
      to.setRebalanceThrottle(from.rebalanceThrottle);
    }
    if (from.rebalanceTimeout != null) {
      to.setRebalanceTimeout(from.rebalanceTimeout);
    }
    if (from.snapshotableIdx != null) {
      to.setSnapshotableIndex(from.snapshotableIdx);
    }
    if (from.storeKeepBinary != null) {
      to.setStoreKeepBinary(from.storeKeepBinary);
    }
    if (from.swapEnabled != null) {
      to.setSwapEnabled(from.swapEnabled);
    }
//    if (from.tmLookupClsName != null) {
//      to.setTransactionManagerLookupClassName(from.tmLookupClsName);
//    }
    if (from.writeBehindEnabled != null) {
      to.setWriteBehindEnabled(from.writeBehindEnabled);
    }
    if (from.writeBehindBatchSize != null) {
      to.setWriteBehindBatchSize(from.writeBehindBatchSize);
    }
    if (from.writeBehindFlushFreq != null) {
      to.setWriteBehindFlushFrequency(from.writeBehindFlushFreq);
    }
    if (from.writeBehindFlushSize != null) {
      to.setWriteBehindFlushSize(from.writeBehindFlushSize);
    }
    if (from.writeBehindFlushThreadCnt != null) {
      to.setWriteBehindFlushThreadCount(from.writeBehindFlushThreadCnt);
    }
    if (from.writeSync != null) {
      to.setWriteSynchronizationMode(from.writeSync);
    }

  }
}

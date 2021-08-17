
package io.ebean.ignite.config;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for l2CacheConfig complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="l2CacheConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="atomicWriteOrderMode" type="{http://ebean-orm.github.io/xml/ns/ignite}cacheAtomicWriteOrderMode" minOccurs="0"/>
 *         &lt;element name="atomicityMode" type="{http://ebean-orm.github.io/xml/ns/ignite}cacheAtomicityMode" minOccurs="0"/>
 *         &lt;element name="backups" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cacheMode" type="{http://ebean-orm.github.io/xml/ns/ignite}cacheMode" minOccurs="0"/>
 *         &lt;element name="cpOnRead" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dfltLockTimeout" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="eagerTtl" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="evictKeyBufSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="evictMaxOverflowRatio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="evictSync" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="evictSyncConcurrencyLvl" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="evictSyncTimeout" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="invalidate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="loadPrevVal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="longQryWarnTimeout" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="maxConcurrentAsyncOps" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="memMode" type="{http://ebean-orm.github.io/xml/ns/ignite}cacheMemoryMode" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nearSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="offHeapMaxMem" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="readFromBackup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="rebalanceBatchSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="rebalanceBatchesPrefetchCount" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rebalanceDelay" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rebalanceMode" type="{http://ebean-orm.github.io/xml/ns/ignite}cacheRebalanceMode" minOccurs="0"/>
 *         &lt;element name="rebalanceOrder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="rebalanceThrottle" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rebalanceTimeout" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="snapshotableIdx" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="startSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="storeConcurrentLoadAllThreshold" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="storeKeepBinary" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="swapEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tmLookupClsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="writeBehindBatchSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="writeBehindEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="writeBehindFlushFreq" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="writeBehindFlushSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="writeBehindFlushThreadCnt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="writeSync" type="{http://ebean-orm.github.io/xml/ns/ignite}cacheWriteSynchronizationMode" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "l2CacheConfig", propOrder = {

})
public class L2CacheConfig {

  @XmlSchemaType(name = "string")
  protected CacheAtomicityMode atomicityMode;
  protected Integer backups;
  @XmlSchemaType(name = "string")
  protected CacheMode cacheMode;
  protected Boolean cpOnRead;
  protected Long dfltLockTimeout;
  protected Boolean eagerTtl;
  protected Integer evictKeyBufSize;
  protected Float evictMaxOverflowRatio;
  protected Boolean evictSync;
  protected Integer evictSyncConcurrencyLvl;
  protected Long evictSyncTimeout;
  protected Boolean invalidate;
  protected Boolean loadPrevVal;
  protected Long longQryWarnTimeout;
  protected Integer maxConcurrentAsyncOps;
  protected String name;
  protected Integer nearSize;
  protected Long offHeapMaxMem;
  protected Boolean readFromBackup;
  protected Integer rebalanceBatchSize;
  protected Long rebalanceBatchesPrefetchCount;
  protected Long rebalanceDelay;
  @XmlSchemaType(name = "string")
  protected CacheRebalanceMode rebalanceMode;
  protected Integer rebalanceOrder;
  protected Long rebalanceThrottle;
  protected Long rebalanceTimeout;
  protected Boolean snapshotableIdx;
  protected Integer startSize;
  protected Integer storeConcurrentLoadAllThreshold;
  protected Boolean storeKeepBinary;
  protected Boolean swapEnabled;
  protected String tmLookupClsName;
  protected Integer writeBehindBatchSize;
  protected Boolean writeBehindEnabled;
  protected Long writeBehindFlushFreq;
  protected Integer writeBehindFlushSize;
  protected Integer writeBehindFlushThreadCnt;
  @XmlSchemaType(name = "string")
  protected CacheWriteSynchronizationMode writeSync;

  /**
   * Gets the value of the atomicityMode property.
   *
   * @return possible object is
   * {@link CacheAtomicityMode }
   */
  public CacheAtomicityMode getAtomicityMode() {
    return atomicityMode;
  }

  /**
   * Sets the value of the atomicityMode property.
   *
   * @param value allowed object is
   *              {@link CacheAtomicityMode }
   */
  public void setAtomicityMode(CacheAtomicityMode value) {
    this.atomicityMode = value;
  }

  /**
   * Gets the value of the backups property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getBackups() {
    return backups;
  }

  /**
   * Sets the value of the backups property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setBackups(Integer value) {
    this.backups = value;
  }

  /**
   * Gets the value of the cacheMode property.
   *
   * @return possible object is
   * {@link CacheMode }
   */
  public CacheMode getCacheMode() {
    return cacheMode;
  }

  /**
   * Sets the value of the cacheMode property.
   *
   * @param value allowed object is
   *              {@link CacheMode }
   */
  public void setCacheMode(CacheMode value) {
    this.cacheMode = value;
  }

  /**
   * Gets the value of the cpOnRead property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isCpOnRead() {
    return cpOnRead;
  }

  /**
   * Sets the value of the cpOnRead property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setCpOnRead(Boolean value) {
    this.cpOnRead = value;
  }

  /**
   * Gets the value of the dfltLockTimeout property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getDfltLockTimeout() {
    return dfltLockTimeout;
  }

  /**
   * Sets the value of the dfltLockTimeout property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setDfltLockTimeout(Long value) {
    this.dfltLockTimeout = value;
  }

  /**
   * Gets the value of the eagerTtl property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isEagerTtl() {
    return eagerTtl;
  }

  /**
   * Sets the value of the eagerTtl property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setEagerTtl(Boolean value) {
    this.eagerTtl = value;
  }

  /**
   * Gets the value of the evictKeyBufSize property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getEvictKeyBufSize() {
    return evictKeyBufSize;
  }

  /**
   * Sets the value of the evictKeyBufSize property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setEvictKeyBufSize(Integer value) {
    this.evictKeyBufSize = value;
  }

  /**
   * Gets the value of the evictMaxOverflowRatio property.
   *
   * @return possible object is
   * {@link Float }
   */
  public Float getEvictMaxOverflowRatio() {
    return evictMaxOverflowRatio;
  }

  /**
   * Sets the value of the evictMaxOverflowRatio property.
   *
   * @param value allowed object is
   *              {@link Float }
   */
  public void setEvictMaxOverflowRatio(Float value) {
    this.evictMaxOverflowRatio = value;
  }

  /**
   * Gets the value of the evictSync property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isEvictSync() {
    return evictSync;
  }

  /**
   * Sets the value of the evictSync property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setEvictSync(Boolean value) {
    this.evictSync = value;
  }

  /**
   * Gets the value of the evictSyncConcurrencyLvl property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getEvictSyncConcurrencyLvl() {
    return evictSyncConcurrencyLvl;
  }

  /**
   * Sets the value of the evictSyncConcurrencyLvl property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setEvictSyncConcurrencyLvl(Integer value) {
    this.evictSyncConcurrencyLvl = value;
  }

  /**
   * Gets the value of the evictSyncTimeout property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getEvictSyncTimeout() {
    return evictSyncTimeout;
  }

  /**
   * Sets the value of the evictSyncTimeout property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setEvictSyncTimeout(Long value) {
    this.evictSyncTimeout = value;
  }

  /**
   * Gets the value of the invalidate property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isInvalidate() {
    return invalidate;
  }

  /**
   * Sets the value of the invalidate property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setInvalidate(Boolean value) {
    this.invalidate = value;
  }

  /**
   * Gets the value of the loadPrevVal property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isLoadPrevVal() {
    return loadPrevVal;
  }

  /**
   * Sets the value of the loadPrevVal property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setLoadPrevVal(Boolean value) {
    this.loadPrevVal = value;
  }

  /**
   * Gets the value of the longQryWarnTimeout property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getLongQryWarnTimeout() {
    return longQryWarnTimeout;
  }

  /**
   * Sets the value of the longQryWarnTimeout property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setLongQryWarnTimeout(Long value) {
    this.longQryWarnTimeout = value;
  }

  /**
   * Gets the value of the maxConcurrentAsyncOps property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getMaxConcurrentAsyncOps() {
    return maxConcurrentAsyncOps;
  }

  /**
   * Sets the value of the maxConcurrentAsyncOps property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setMaxConcurrentAsyncOps(Integer value) {
    this.maxConcurrentAsyncOps = value;
  }

  /**
   * Gets the value of the name property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Gets the value of the nearSize property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getNearSize() {
    return nearSize;
  }

  /**
   * Sets the value of the nearSize property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setNearSize(Integer value) {
    this.nearSize = value;
  }

  /**
   * Gets the value of the offHeapMaxMem property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getOffHeapMaxMem() {
    return offHeapMaxMem;
  }

  /**
   * Sets the value of the offHeapMaxMem property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setOffHeapMaxMem(Long value) {
    this.offHeapMaxMem = value;
  }

  /**
   * Gets the value of the readFromBackup property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isReadFromBackup() {
    return readFromBackup;
  }

  /**
   * Sets the value of the readFromBackup property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setReadFromBackup(Boolean value) {
    this.readFromBackup = value;
  }

  /**
   * Gets the value of the rebalanceBatchSize property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getRebalanceBatchSize() {
    return rebalanceBatchSize;
  }

  /**
   * Sets the value of the rebalanceBatchSize property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setRebalanceBatchSize(Integer value) {
    this.rebalanceBatchSize = value;
  }

  /**
   * Gets the value of the rebalanceBatchesPrefetchCount property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getRebalanceBatchesPrefetchCount() {
    return rebalanceBatchesPrefetchCount;
  }

  /**
   * Sets the value of the rebalanceBatchesPrefetchCount property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setRebalanceBatchesPrefetchCount(Long value) {
    this.rebalanceBatchesPrefetchCount = value;
  }

  /**
   * Gets the value of the rebalanceDelay property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getRebalanceDelay() {
    return rebalanceDelay;
  }

  /**
   * Sets the value of the rebalanceDelay property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setRebalanceDelay(Long value) {
    this.rebalanceDelay = value;
  }

  /**
   * Gets the value of the rebalanceMode property.
   *
   * @return possible object is
   * {@link CacheRebalanceMode }
   */
  public CacheRebalanceMode getRebalanceMode() {
    return rebalanceMode;
  }

  /**
   * Sets the value of the rebalanceMode property.
   *
   * @param value allowed object is
   *              {@link CacheRebalanceMode }
   */
  public void setRebalanceMode(CacheRebalanceMode value) {
    this.rebalanceMode = value;
  }

  /**
   * Gets the value of the rebalanceOrder property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getRebalanceOrder() {
    return rebalanceOrder;
  }

  /**
   * Sets the value of the rebalanceOrder property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setRebalanceOrder(Integer value) {
    this.rebalanceOrder = value;
  }

  /**
   * Gets the value of the rebalanceThrottle property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getRebalanceThrottle() {
    return rebalanceThrottle;
  }

  /**
   * Sets the value of the rebalanceThrottle property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setRebalanceThrottle(Long value) {
    this.rebalanceThrottle = value;
  }

  /**
   * Gets the value of the rebalanceTimeout property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getRebalanceTimeout() {
    return rebalanceTimeout;
  }

  /**
   * Sets the value of the rebalanceTimeout property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setRebalanceTimeout(Long value) {
    this.rebalanceTimeout = value;
  }

  /**
   * Gets the value of the snapshotableIdx property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isSnapshotableIdx() {
    return snapshotableIdx;
  }

  /**
   * Sets the value of the snapshotableIdx property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setSnapshotableIdx(Boolean value) {
    this.snapshotableIdx = value;
  }

  /**
   * Gets the value of the startSize property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getStartSize() {
    return startSize;
  }

  /**
   * Sets the value of the startSize property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setStartSize(Integer value) {
    this.startSize = value;
  }

  /**
   * Gets the value of the storeConcurrentLoadAllThreshold property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getStoreConcurrentLoadAllThreshold() {
    return storeConcurrentLoadAllThreshold;
  }

  /**
   * Sets the value of the storeConcurrentLoadAllThreshold property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setStoreConcurrentLoadAllThreshold(Integer value) {
    this.storeConcurrentLoadAllThreshold = value;
  }

  /**
   * Gets the value of the storeKeepBinary property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isStoreKeepBinary() {
    return storeKeepBinary;
  }

  /**
   * Sets the value of the storeKeepBinary property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setStoreKeepBinary(Boolean value) {
    this.storeKeepBinary = value;
  }

  /**
   * Gets the value of the swapEnabled property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isSwapEnabled() {
    return swapEnabled;
  }

  /**
   * Sets the value of the swapEnabled property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setSwapEnabled(Boolean value) {
    this.swapEnabled = value;
  }

  /**
   * Gets the value of the tmLookupClsName property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getTmLookupClsName() {
    return tmLookupClsName;
  }

  /**
   * Sets the value of the tmLookupClsName property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setTmLookupClsName(String value) {
    this.tmLookupClsName = value;
  }

  /**
   * Gets the value of the writeBehindBatchSize property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getWriteBehindBatchSize() {
    return writeBehindBatchSize;
  }

  /**
   * Sets the value of the writeBehindBatchSize property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setWriteBehindBatchSize(Integer value) {
    this.writeBehindBatchSize = value;
  }

  /**
   * Gets the value of the writeBehindEnabled property.
   *
   * @return possible object is
   * {@link Boolean }
   */
  public Boolean isWriteBehindEnabled() {
    return writeBehindEnabled;
  }

  /**
   * Sets the value of the writeBehindEnabled property.
   *
   * @param value allowed object is
   *              {@link Boolean }
   */
  public void setWriteBehindEnabled(Boolean value) {
    this.writeBehindEnabled = value;
  }

  /**
   * Gets the value of the writeBehindFlushFreq property.
   *
   * @return possible object is
   * {@link Long }
   */
  public Long getWriteBehindFlushFreq() {
    return writeBehindFlushFreq;
  }

  /**
   * Sets the value of the writeBehindFlushFreq property.
   *
   * @param value allowed object is
   *              {@link Long }
   */
  public void setWriteBehindFlushFreq(Long value) {
    this.writeBehindFlushFreq = value;
  }

  /**
   * Gets the value of the writeBehindFlushSize property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getWriteBehindFlushSize() {
    return writeBehindFlushSize;
  }

  /**
   * Sets the value of the writeBehindFlushSize property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setWriteBehindFlushSize(Integer value) {
    this.writeBehindFlushSize = value;
  }

  /**
   * Gets the value of the writeBehindFlushThreadCnt property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getWriteBehindFlushThreadCnt() {
    return writeBehindFlushThreadCnt;
  }

  /**
   * Sets the value of the writeBehindFlushThreadCnt property.
   *
   * @param value allowed object is
   *              {@link Integer }
   */
  public void setWriteBehindFlushThreadCnt(Integer value) {
    this.writeBehindFlushThreadCnt = value;
  }

  /**
   * Gets the value of the writeSync property.
   *
   * @return possible object is
   * {@link CacheWriteSynchronizationMode }
   */
  public CacheWriteSynchronizationMode getWriteSync() {
    return writeSync;
  }

  /**
   * Sets the value of the writeSync property.
   *
   * @param value allowed object is
   *              {@link CacheWriteSynchronizationMode }
   */
  public void setWriteSync(CacheWriteSynchronizationMode value) {
    this.writeSync = value;
  }

}

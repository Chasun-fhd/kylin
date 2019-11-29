/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.engine.spark.metadata.cube.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.kylin.common.KylinConfig;
import org.apache.kylin.common.persistence.RootPersistentEntity;
import org.apache.kylin.engine.spark.metadata.Measure;
import org.apache.kylin.metadata.model.JoinTableDesc;
import org.apache.kylin.metadata.model.JoinsTree;
import org.apache.kylin.metadata.model.PartitionDesc;
import org.apache.kylin.metadata.model.TableDesc;
import org.apache.kylin.metadata.model.TableRef;
import org.apache.kylin.metadata.model.TblColRef;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class DataModel extends RootPersistentEntity {
    public enum TableKind implements Serializable {
        FACT, LOOKUP
    }

    public enum RealizationCapacity implements Serializable {
        SMALL, MEDIUM, LARGE
    }

    private KylinConfig config;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("config_last_modifier")
    private String configLastModifier;

    @JsonProperty("config_last_modified")
    private long configLastModified;

    @JsonProperty("is_draft")
    private boolean isDraft;

    @JsonProperty("description")
    private String description;

    @JsonProperty("fact_table")
    private String rootFactTableName;

    @JsonProperty("fact_table_alias")
    private String rootFactTableAlias;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<JoinTableDesc> joinTables;

    @JsonProperty("filter_condition")
    private String filterCondition;

    @JsonProperty("partition_desc")
    private PartitionDesc partitionDesc;

    @JsonProperty("capacity")
    private RealizationCapacity capacity = RealizationCapacity.MEDIUM;

    private ImmutableBiMap<Integer, MeasureDesc> effectiveMeasures; // excluding DELETED cols

    // computed attributes
    private TableRef rootFactTableRef;
    private Set<TableRef> factTableRefs = Sets.newLinkedHashSet();
    private Set<TableRef> lookupTableRefs = Sets.newLinkedHashSet();
    private Set<TableRef> allTableRefs = Sets.newLinkedHashSet();
    private Map<String, TableRef> aliasMap = Maps.newHashMap(); // alias => TableRef, a table has exactly one alias
    private Map<String, TableRef> tableNameMap = Maps.newHashMap(); // name => TableRef, a table maybe referenced by multiple names
    private JoinsTree joinsTree;

    /**
     * returns ID <==> Measure
     */
    public ImmutableBiMap<Integer, MeasureDesc> getEffectiveMeasureMap() {
        return effectiveMeasures;
    }

    public KylinConfig getConfig() {
        return config;
    }

    public void setConfig(KylinConfig config) {
        this.config = config;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getConfigLastModifier() {
        return configLastModifier;
    }

    public void setConfigLastModifier(String configLastModifier) {
        this.configLastModifier = configLastModifier;
    }

    public long getConfigLastModified() {
        return configLastModified;
    }

    public void setConfigLastModified(long configLastModified) {
        this.configLastModified = configLastModified;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRootFactTableName() {
        return rootFactTableName;
    }

    public void setRootFactTableName(String rootFactTableName) {
        this.rootFactTableName = rootFactTableName;
    }

    public String getRootFactTableAlias() {
        return rootFactTableAlias;
    }

    public void setRootFactTableAlias(String rootFactTableAlias) {
        this.rootFactTableAlias = rootFactTableAlias;
    }

    public List<JoinTableDesc> getJoinTables() {
        return joinTables;
    }

    public void setJoinTables(List<JoinTableDesc> joinTables) {
        this.joinTables = joinTables;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public PartitionDesc getPartitionDesc() {
        return partitionDesc;
    }

    public void setPartitionDesc(PartitionDesc partitionDesc) {
        this.partitionDesc = partitionDesc;
    }

    public RealizationCapacity getCapacity() {
        return capacity;
    }

    public void setCapacity(RealizationCapacity capacity) {
        this.capacity = capacity;
    }

    public TableRef getRootFactTableRef() {
        return rootFactTableRef;
    }

    public void setRootFactTableRef(TableRef rootFactTableRef) {
        this.rootFactTableRef = rootFactTableRef;
    }

    public Set<TableRef> getFactTableRefs() {
        return factTableRefs;
    }

    public void setFactTableRefs(Set<TableRef> factTableRefs) {
        this.factTableRefs = factTableRefs;
    }

    public Set<TableRef> getLookupTableRefs() {
        return lookupTableRefs;
    }

    public void setLookupTableRefs(Set<TableRef> lookupTableRefs) {
        this.lookupTableRefs = lookupTableRefs;
    }

    public Set<TableRef> getAllTableRefs() {
        return allTableRefs;
    }

    public void setAllTableRefs(Set<TableRef> allTableRefs) {
        this.allTableRefs = allTableRefs;
    }

    public Map<String, TableRef> getAliasMap() {
        return aliasMap;
    }

    public void setAliasMap(Map<String, TableRef> aliasMap) {
        this.aliasMap = aliasMap;
    }

    public Map<String, TableRef> getTableNameMap() {
        return tableNameMap;
    }

    public void setTableNameMap(Map<String, TableRef> tableNameMap) {
        this.tableNameMap = tableNameMap;
    }

    public JoinsTree getJoinsTree() {
        return joinsTree;
    }

    public void setJoinsTree(JoinsTree joinsTree) {
        this.joinsTree = joinsTree;
    }

    // find by unique name, that must uniquely identifies a table in the model
    public TableRef findTable(String table) throws IllegalArgumentException {
        TableRef result = tableNameMap.get(table.toUpperCase(Locale.ROOT));
        if (result == null) {
            throw new IllegalArgumentException("Table not found by " + table);
        }
        return result;
    }

    public TblColRef findColumn(String table, String column) throws IllegalArgumentException {
        TableRef tableRef = findTable(table);
        TblColRef result = tableRef.getColumn(column.toUpperCase(Locale.ROOT));
        if (result == null)
            throw new IllegalArgumentException("Column not found by " + table + "." + column);
        return result;
    }

    public TblColRef findColumn(String column) throws IllegalArgumentException {
        TblColRef result = null;
        String input = column;

        column = column.toUpperCase(Locale.ROOT);
        int cut = column.lastIndexOf('.');
        if (cut > 0) {
            // table specified
            result = findColumn(column.substring(0, cut), column.substring(cut + 1));
        } else {
            // table not specified, try each table
            for (TableRef tableRef : allTableRefs) {
                result = tableRef.getColumn(column);
                if (result != null)
                    break;
            }
        }

        if (result == null)
            throw new IllegalArgumentException("Column not found by " + input);

        return result;
    }

    // find by table identity, that may match multiple tables in the model
    public TableRef findFirstTable(String tableIdentity) throws IllegalArgumentException {
        if (rootFactTableRef.getTableIdentity().equals(tableIdentity))
            return rootFactTableRef;

        for (TableRef fact : factTableRefs) {
            if (fact.getTableIdentity().equals(tableIdentity))
                return fact;
        }

        for (TableRef lookup : lookupTableRefs) {
            if (lookup.getTableIdentity().equals(tableIdentity))
                return lookup;
        }
        throw new IllegalArgumentException("Table not found by " + tableIdentity + " in model " + name);
    }

    @Override
    public String toString() {
        return "Cube [" + alias + "]";
    }

    public void initInternal(KylinConfig config, Map<String, TableDesc> tables) {
        this.config = config;

        initJoinTablesForUpgrade();
        initTableAlias(tables);
        initJoinColumns();
        reorderJoins(tables);
        initJoinsTree();
        initDimensionsAndMetrics();
        initPartitionDesc();
        initFilterCondition();

        boolean reinit = validate();
        if (reinit) { // model slightly changed by validate() and must init() again
            initInternal(config, tables);
        }
    }
}
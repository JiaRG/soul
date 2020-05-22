/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.dromara.soul.cache.api;

import com.google.common.collect.Maps;
import org.dromara.soul.common.dto.AppAuthData;
import org.dromara.soul.common.dto.MetaData;
import org.dromara.soul.common.dto.PluginData;
import org.dromara.soul.common.dto.RuleData;
import org.dromara.soul.common.dto.SelectorData;
import org.dromara.soul.common.utils.PathMatchUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/**
 * Implements the main method of LookupCacheManager, providing an API for updating cache operations.
 *
 * @author huangxiaofeng
 */
public abstract class AbstractLocalCacheManager implements LocalCacheManager {

    /**
     * pluginName -> PluginData.
     */
    public static final ConcurrentMap<String, PluginData> PLUGIN_MAP = Maps.newConcurrentMap();

    /**
     * pluginName -> SelectorData.
     */
    public static final ConcurrentMap<String, List<SelectorData>> SELECTOR_MAP = Maps.newConcurrentMap();

    /**
     * selectorId -> RuleData.
     */
    public  static final ConcurrentMap<String, List<RuleData>> RULE_MAP = Maps.newConcurrentMap();

    /**
     * appKey -> AppAuthData.
     */
    public static final ConcurrentMap<String, AppAuthData> AUTH_MAP = Maps.newConcurrentMap();

    /**
     * path-> MetaData.
     */
    public  static final ConcurrentMap<String, MetaData> META_DATA = Maps.newConcurrentMap();

    /**
     * acquire AppAuthData by appKey with AUTH_MAP container.
     *
     * @param appKey this is appKey.
     * @return AppAuthData {@linkplain AppAuthData}
     */
    @Override
    public AppAuthData findAuthDataByAppKey(final String appKey) {
        return AUTH_MAP.get(appKey);
    }

    /**
     * acquire PluginData by pluginName with PLUGIN_MAP container.
     *
     * @param pluginName this is plugin name.
     * @return PluginData {@linkplain  PluginData}
     */
    @Override
    public PluginData findPluginByName(final String pluginName) {
        return PLUGIN_MAP.get(pluginName);
    }

    /**
     * acquire SelectorData list  by pluginName with  SELECTOR_MAP HashMap container.
     *
     * @param pluginName this is plugin name.
     * @return SelectorData list {@linkplain  SelectorData}
     */
    @Override
    public List<SelectorData> findSelectorByPluginName(final String pluginName) {
        return SELECTOR_MAP.get(pluginName);
    }

    /**
     * acquire RuleData list by selectorId with  RULE_MAP HashMap container.
     *
     * @param selectorId this is selectorId.
     * @return RuleData list {@linkplain  RuleData}
     */
    @Override
    public List<RuleData> findRuleBySelectorId(final String selectorId) {
        return RULE_MAP.get(selectorId);
    }
    
    @Override
    public MetaData findByUri(String uri) {
        MetaData metaData = META_DATA.get(uri);
        if (Objects.isNull(metaData)) {
            String key = META_DATA.keySet().stream().filter(k -> PathMatchUtils.match(k, uri)).findFirst().orElse("");
            return META_DATA.get(key);
        }
        return metaData;
    }
}

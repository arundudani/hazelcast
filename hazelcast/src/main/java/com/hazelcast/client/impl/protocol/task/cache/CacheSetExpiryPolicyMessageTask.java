/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.client.impl.protocol.task.cache;

import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.codec.CacheSetExpiryPolicyCodec;
import com.hazelcast.instance.impl.Node;
import com.hazelcast.internal.nio.Connection;
import com.hazelcast.security.permission.ActionConstants;
import com.hazelcast.security.permission.CachePermission;
import com.hazelcast.spi.impl.operationservice.Operation;

import java.security.Permission;

public class CacheSetExpiryPolicyMessageTask extends AbstractCacheMessageTask<CacheSetExpiryPolicyCodec.RequestParameters> {

    public CacheSetExpiryPolicyMessageTask(ClientMessage message, Node node, Connection connection) {
        super(message, node, connection);
    }

    @Override
    protected Operation prepareOperation() {
        return getOperationProvider(parameters.name)
                .createSetExpiryPolicyOperation(parameters.keys, parameters.expiryPolicy);
    }

    @Override
    protected CacheSetExpiryPolicyCodec.RequestParameters decodeClientMessage(ClientMessage clientMessage) {
        return CacheSetExpiryPolicyCodec.decodeRequest(clientMessage);
    }

    @Override
    protected ClientMessage encodeResponse(Object response) {
        return CacheSetExpiryPolicyCodec.encodeResponse((Boolean) response);
    }

    @Override
    public String getDistributedObjectName() {
        return parameters.name;
    }

    @Override
    public String getMethodName() {
        return "setExpiryPolicy";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{parameters.keys, parameters.expiryPolicy};
    }

    @Override
    public Permission getRequiredPermission() {
        return new CachePermission(parameters.name, ActionConstants.ACTION_REMOVE);
    }
}

package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Extension.ExtensionType;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExtensionRegistry extends ExtensionRegistryLite {
    private static final ExtensionRegistry EMPTY = new ExtensionRegistry(true);
    private final Map immutableExtensionsByName;
    private final Map immutableExtensionsByNumber;
    private final Map mutableExtensionsByName;
    private final Map mutableExtensionsByNumber;

    final class DescriptorIntPair {
        private final Descriptor descriptor;
        private final int number;

        DescriptorIntPair(Descriptor descriptor, int i) {
            this.descriptor = descriptor;
            this.number = i;
        }

        public boolean equals(Object obj) {
            if (obj instanceof DescriptorIntPair) {
                DescriptorIntPair descriptorIntPair = (DescriptorIntPair) obj;
                if (this.descriptor == descriptorIntPair.descriptor && this.number == descriptorIntPair.number) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (65535 * this.descriptor.hashCode()) + this.number;
        }
    }

    public final class ExtensionInfo {
        public final Message defaultInstance;
        public final FieldDescriptor descriptor;

        private ExtensionInfo(FieldDescriptor fieldDescriptor) {
            this.descriptor = fieldDescriptor;
            this.defaultInstance = null;
        }

        private ExtensionInfo(FieldDescriptor fieldDescriptor, Message message) {
            this.descriptor = fieldDescriptor;
            this.defaultInstance = message;
        }
    }

    private ExtensionRegistry() {
        this.immutableExtensionsByName = new HashMap();
        this.mutableExtensionsByName = new HashMap();
        this.immutableExtensionsByNumber = new HashMap();
        this.mutableExtensionsByNumber = new HashMap();
    }

    private ExtensionRegistry(ExtensionRegistry extensionRegistry) {
        super((ExtensionRegistryLite) extensionRegistry);
        this.immutableExtensionsByName = Collections.unmodifiableMap(extensionRegistry.immutableExtensionsByName);
        this.mutableExtensionsByName = Collections.unmodifiableMap(extensionRegistry.mutableExtensionsByName);
        this.immutableExtensionsByNumber = Collections.unmodifiableMap(extensionRegistry.immutableExtensionsByNumber);
        this.mutableExtensionsByNumber = Collections.unmodifiableMap(extensionRegistry.mutableExtensionsByNumber);
    }

    ExtensionRegistry(boolean z) {
        super(ExtensionRegistryLite.getEmptyRegistry());
        this.immutableExtensionsByName = Collections.emptyMap();
        this.mutableExtensionsByName = Collections.emptyMap();
        this.immutableExtensionsByNumber = Collections.emptyMap();
        this.mutableExtensionsByNumber = Collections.emptyMap();
    }

    private void add(ExtensionInfo extensionInfo, ExtensionType extensionType) {
        if (extensionInfo.descriptor.isExtension()) {
            Map map;
            Map map2;
            switch (extensionType) {
                case IMMUTABLE:
                    map = this.immutableExtensionsByName;
                    map2 = this.immutableExtensionsByNumber;
                    break;
                case MUTABLE:
                    map = this.mutableExtensionsByName;
                    map2 = this.mutableExtensionsByNumber;
                    break;
                default:
                    return;
            }
            map.put(extensionInfo.descriptor.getFullName(), extensionInfo);
            map2.put(new DescriptorIntPair(extensionInfo.descriptor.getContainingType(), extensionInfo.descriptor.getNumber()), extensionInfo);
            FieldDescriptor fieldDescriptor = extensionInfo.descriptor;
            if (fieldDescriptor.getContainingType().getOptions().getMessageSetWireFormat() && fieldDescriptor.getType() == Type.MESSAGE && fieldDescriptor.isOptional() && fieldDescriptor.getExtensionScope() == fieldDescriptor.getMessageType()) {
                map.put(fieldDescriptor.getMessageType().getFullName(), extensionInfo);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("ExtensionRegistry.add() was given a FieldDescriptor for a regular (non-extension) field.");
    }

    public static ExtensionRegistry getEmptyRegistry() {
        return EMPTY;
    }

    static ExtensionInfo newExtensionInfo(Extension extension) {
        if (extension.getDescriptor().getJavaType() != JavaType.MESSAGE) {
            return new ExtensionInfo(extension.getDescriptor(), null);
        }
        if (extension.getMessageDefaultInstance() != null) {
            return new ExtensionInfo(extension.getDescriptor(), (Message) extension.getMessageDefaultInstance());
        }
        throw new IllegalStateException("Registered message-type extension had null default instance: " + extension.getDescriptor().getFullName());
    }

    public static ExtensionRegistry newInstance() {
        return new ExtensionRegistry();
    }

    public void add(FieldDescriptor fieldDescriptor) {
        if (fieldDescriptor.getJavaType() == JavaType.MESSAGE) {
            throw new IllegalArgumentException("ExtensionRegistry.add() must be provided a default instance when adding an embedded message extension.");
        }
        ExtensionInfo extensionInfo = new ExtensionInfo(fieldDescriptor, null);
        add(extensionInfo, ExtensionType.IMMUTABLE);
        add(extensionInfo, ExtensionType.MUTABLE);
    }

    public void add(FieldDescriptor fieldDescriptor, Message message) {
        if (fieldDescriptor.getJavaType() != JavaType.MESSAGE) {
            throw new IllegalArgumentException("ExtensionRegistry.add() provided a default instance for a non-message extension.");
        }
        add(new ExtensionInfo(fieldDescriptor, message), ExtensionType.IMMUTABLE);
    }

    public void add(Extension extension) {
        if (extension.getExtensionType() == ExtensionType.IMMUTABLE || extension.getExtensionType() == ExtensionType.MUTABLE) {
            add(newExtensionInfo(extension), extension.getExtensionType());
        }
    }

    public ExtensionInfo findExtensionByName(String str) {
        return findImmutableExtensionByName(str);
    }

    public ExtensionInfo findExtensionByNumber(Descriptor descriptor, int i) {
        return findImmutableExtensionByNumber(descriptor, i);
    }

    public ExtensionInfo findImmutableExtensionByName(String str) {
        return (ExtensionInfo) this.immutableExtensionsByName.get(str);
    }

    public ExtensionInfo findImmutableExtensionByNumber(Descriptor descriptor, int i) {
        return (ExtensionInfo) this.immutableExtensionsByNumber.get(new DescriptorIntPair(descriptor, i));
    }

    public ExtensionInfo findMutableExtensionByName(String str) {
        return (ExtensionInfo) this.mutableExtensionsByName.get(str);
    }

    public ExtensionInfo findMutableExtensionByNumber(Descriptor descriptor, int i) {
        return (ExtensionInfo) this.mutableExtensionsByNumber.get(new DescriptorIntPair(descriptor, i));
    }

    public Set getAllImmutableExtensionsByExtendedType(String str) {
        Set hashSet = new HashSet();
        for (DescriptorIntPair descriptorIntPair : this.immutableExtensionsByNumber.keySet()) {
            if (descriptorIntPair.descriptor.getFullName().equals(str)) {
                hashSet.add(this.immutableExtensionsByNumber.get(descriptorIntPair));
            }
        }
        return hashSet;
    }

    public Set getAllMutableExtensionsByExtendedType(String str) {
        Set hashSet = new HashSet();
        for (DescriptorIntPair descriptorIntPair : this.mutableExtensionsByNumber.keySet()) {
            if (descriptorIntPair.descriptor.getFullName().equals(str)) {
                hashSet.add(this.mutableExtensionsByNumber.get(descriptorIntPair));
            }
        }
        return hashSet;
    }

    public ExtensionRegistry getUnmodifiable() {
        return new ExtensionRegistry(this);
    }
}

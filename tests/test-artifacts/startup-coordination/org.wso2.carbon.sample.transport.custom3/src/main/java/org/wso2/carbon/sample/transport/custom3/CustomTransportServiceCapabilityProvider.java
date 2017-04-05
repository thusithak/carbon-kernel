package org.wso2.carbon.sample.transport.custom3;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.kernel.startupresolver.CapabilityProvider;
import org.wso2.carbon.sample.transport.mgt.Transport;

/**
 * Sample TransportServiceCapabilityProvider class that registers CustomTransport as a service multiple times to test
 * the startup order resolver implementation.
 *
 * @since 5.0.0
 */
@Component(
        name = "org.wso2.carbon.sample.transport.http.CustomTransportServiceCapabilityProvider",
        immediate = true,
        property = "capabilityName=org.wso2.carbon.sample.transport.mgt.Transport"
)
public class CustomTransportServiceCapabilityProvider implements CapabilityProvider {
    private static final int customTransportServiceCount = 3;

    @Activate
    protected void start(BundleContext bundleContext) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < customTransportServiceCount; i++) {
                bundleContext.registerService(Transport.class, new Custom3Transport(), null);

                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException ignore) {
                }
            }
        });
        thread.start();
    }

    @Override
    public int getCount() {
        return customTransportServiceCount;
    }
}

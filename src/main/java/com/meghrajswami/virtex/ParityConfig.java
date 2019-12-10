package com.meghrajswami.virtex;

import com.paritytrading.parity.util.Instruments;
import com.typesafe.config.Config;

public class ParityConfig {
    private Instruments instruments;

    ParityConfig(Config config) {
        instruments = Instruments.fromConfig(config, "instruments");
    }

    public Instruments getInstruments() {
        return instruments;
    }
}

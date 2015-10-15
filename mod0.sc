SynthDef("fmNoiseDrone",
	{
		arg freq = 440, divider = 12, out = [0, 1], amp = 1.0;
		var ampOsc, freqOsc, noiseOsc;
		Out.ar(out,
			ampOsc = SinOsc.kr(0.1, 0, 0.5, 0.5);
			freqOsc = SinOsc.ar(freq/divider, 0, 0.5, 0.5);
			noiseOsc = PinkNoise.ar(1);
			SinOsc.ar(noiseOsc*freqOsc*(0.3 + freq), out, 0.5 + (ampOsc));
		);
}).send(s);

x = Synth("fmNoiseDrone", ["freq", 150, "amp", 2]);

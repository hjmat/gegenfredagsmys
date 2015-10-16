SynthDef("fmNoiseDrone",
	{
		arg freq = 440, divider = 12, out = [0, 1], amp = 0.1;
		var ampOsc, freqOsc, noiseOsc;
		ampOsc = SinOsc.kr(0.1, 0, 0.5, 0.5);
		freqOsc = SinOsc.ar(freq/divider, 0, 0.5, 0.5);
		noiseOsc = PinkNoise.ar(1);
		Out.ar(out,
			SinOsc.ar(noiseOsc*freqOsc*(0.3 + freq), out, (0.5 + ampOsc))*amp;
		);
}).send(s);

x = Synth("fmNoiseDrone", ["freq", 100]);

// drums
SynthDef(\perc,
	{
		arg freq = 200, out = [0, 1], amp = 1.0, atk = 0;
		var env, crackle;
		env = EnvGen.kr(Env.perc(0.01, atk), doneAction:2);
		crackle = Crackle.ar(1.0);
		Out.ar(out,
			(SinOsc.ar(freq*env)+crackle)*env*amp;
		);
}).send(s);

(
Pbind(
    \instrument, \perc,
    \dur, Prand([1/4, 1/4, 1/4, 1/16], inf),
    \atk, Pwrand([0.01, 0.1, 1.0, 0.1], [1, 1, 1, 1].normalizeSum, inf),
	\freq, Prand([200, 200, 2000, 100], inf)
).play(quant: 4);
)

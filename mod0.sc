// noise drone
SynthDef(\noiseDrone,
	{
		arg freq = 440, divider = 12, out = [0, 1];
		var ampOsc, freqOsc, noiseOsc, env;
		ampOsc = SinOsc.kr(0.1, 0, 0.5, 0.5);
		freqOsc = SinOsc.ar(freq/divider, 0, 0.5, 0.5);
		env = EnvGen.kr(Env.asr(1.0, 0.7, 1.0));
		noiseOsc = PinkNoise.ar(0.05);
		Out.ar(out,
			SinOsc.ar((noiseOsc)+freqOsc*(0.3 + freq), out, (0.5 + ampOsc))*0.01*env;
		);
}).send(s);

// fm percussion
SynthDef(\perc,
	{
		arg freq = 200, out = [0, 1], amp = 1.0;
		var env, noiseOsc;
		noiseOsc = PinkNoise.kr(0.5);
		env = EnvGen.kr(Env.perc(0.01, noiseOsc), doneAction:2);
		Out.ar(out,
			(SinOsc.ar(freq*env))*env*amp;
		);
}).send(s);

// Drone
Pbind(
	\instrument, \noiseDrone,
	\dur, Pseq([1/4, 1/1,1/32], inf),
	\freq, Prand([100, 800, 200, 100], inf)
).play(quant: 4);

// Drone 2
Pbind(
	\instrument, \noiseDrone,
	\dur, Pseq([1/1], inf),
	\freq, Prand([100], inf)
).play(quant: 4);

// Kick
Pbind(
	\instrument, \perc,
	\dur, Pseq([1/2], inf),
	\freq, Pseq([100], inf)
).play(quant: 4);

// Snare
Pbind(
	\instrument, \perc,
	\dur, Prand([21/9, 11/2, 1/32], inf),
	\freq, Prand([4000, 1000], inf)
).play(quant: 3);
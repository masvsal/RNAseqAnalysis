##Personal Project

###Samuel Salitra - 10/1/21

Questions to Answer:
- **What will the Application Do?** </br>
It will be a file system that a biologist might use to keep track of their experiments. Additionally, it will have the functionality to perform a simply analysis of any RNAseq data file in its file system. 
- **Who will use it?** </br>
Bioinformaticians might use it in their day-to-day work to keep track of their work and automate the tedious task of analyzing RNA sequence data.
- **Why is this project of interest to you?** </br>
I would like to work in bioinformatics so I want to understand the basic workflow of a typical bioinformatic program.

Use at least two of:
- bold/italic
- title/subtitle
- bulleted list

An example of text with **bold** and *italic* fonts.  Note that the IntelliJ markdown previewer doesn't seem to render 
the bold and italic fonts correctly but they will appear correctly on GitHub.

####User Stories

- As a user, I want to add be able to add or remove an experiment to the directory of my experiments
- As a user, I want to be able to change the description of an experiment
- As a user, I want to be able to add or remove a data file to an experiment
- As a user, I want to set a significance threshold and find significant differences between two RNAseq data files contained within one of my experiments.
####TODO

- add the following user interaction: Add/remove experiments (DONE), add/remove data files (DONE), 
add description to experiments and data files (DONE)
- problem: when I add an experiment, go into the experiment and quit out to experiment directory, 
the experiment is gone. (FIXED, removed extra initialization)
- reduce  code duplication in add/remove data files/experiments (partway done)
- add new data file-level selection screen (DONE)
- abstract qPCR and RNAseq data (what are the fields, what operations to do to them)
- add independent display data file method (DONE)
- assign marker gene that can be used for marker comparison
- search for above threshold
- google fpKm



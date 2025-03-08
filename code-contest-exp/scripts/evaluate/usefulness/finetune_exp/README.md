# Pie experiment
original repo: https://github.com/LearningOpt/pie.git

paper link: https://openreview.net/forum?id=ix7rLVHXyY

## Steps to run pie experiment
### Setting PYTHONPATH
```python
cd code-contest-exp
export PYTHONPATH=$(pwd)/scripts:../feedback_collection
```
set problem list: 

current list (199 problems in total): ["911_C", "1118_D1", "515_B", "1345_B", "815_B", "63_B", "633_A", "40_B", "1225_D", "192_A", "1184_C1", "1184_A1", "301_B", "1140_D", "1328_B", "787_A", "477_A", "903_A", "16_B", "189_A", "937_B", "351_E", "392_A", "520_B", "681_B", "134_B", "546_C", "1067_B", "808_E", "1322_B", "559_C", "846_B", "1204_E", "289_D", "469_B", "288_B", "1243_D", "480_C", "148_A", "803_D", "999_F", "768_C", "1263_C", "990_B", "1057_C", "1061_C", "991_E", "734_B", "1260_D", "171_F", "44_B", "321_A", "1209_B", "1210_A", "1228_E", "1286_A", "773_B", "1107_E", "1237_E", "1337_E", "932_E", "1165_F1", "1172_B", "1139_D", "938_B", "706_D", "1213_D1", "1230_C", "131_E", "675_E", "479_E", "1430_E", "513_C", "68_B", "996_A", "845_B", "543_A", "768_E", "476_C", "569_A", "958_A1", "181_B", "626_C", "626_B", "1422_E", "687_C", "525_C", "827_A", "1428_E", "922_A", "132_C", "484_B", "1064_A", "1173_D", "1334_E", "1242_B", "731_F", "312_B", "821_B", "31_C", "898_E", "1152_D", "529_E", "566_F", "1129_A2", "583_D", "349_B", "1225_B1", "457_A", "910_C", "1384_B1", "244_B", "235_A", "1114_C", "922_B", "1398_F", "1201_C", "804_B", "535_B", "988_F", "799_C", "1195_B", "366_C", "863_B", "1253_E", "690_F1", "1422_C", "1041_A", "962_C", "1284_B", "48_C", "1401_E", "662_D", "1475_G", "1131_E", "1141_A", "1409_F", "239_A", "1350_A", "1327_A", "552_C", "452_C", "55_A", "1424_J", "1082_E", "1097_C", "1397_B", "1102_A", "1081_C", "923_A", "803_F", "23_A", "485_A", "736_B", "278_B", "98_A", "1333_F", "1342_E", "1359_E", "108_D", "747_C", "577_A", "608_C", "237_C", "610_A", "1246_A", "2_A", "452_D", "1413_C", "554_C", "1375_G", "958_E1", "690_C1", "993_A", "304_A", "910_B", "1203_D1", "1408_D", "713_C", "148_E", "791_B", "1228_C", "1433_F", "431_C", "598_D", "868_C", "1062_B", "999_E", "909_B", "1225_C", "255_D", "1404_C", "848_B", "996_B", "111_B", "1445_D", "1288_C", "1261_B1", "255_C"]

### Edit solution
```python
python scripts/evaluate/usefulness/finetune_exp/edit_solution.py --checkpoint LearningOpt/pie-hq-selfplay-13b --backend hf --input_set alphacode --input_selection_type none
```

checkpoint: [LearningOpt/pie-hq-selfplay-13b, LearningOpt/pie-conditioned-13b, LearningOpt/pie-all-uncon-13b]
input_set + input_selection_type: [
    alphacode + none,
    alphacode + public_private_slow_5,
    corpus_instrument_fuzz_mutator_with_constraint_per_solution + slow_5,
]

### Profile
```python
python scripts/evaluate/usefulness/finetune_exp/profile_solution.py --input_set alphacode --input_selection_type all_include_generated --model_name pie-hq-selfplay-13b
```

model_name: [pie-hq-selfplay-13b, pie-conditioned-13b, pie-all-uncon-13b]
input_set + input_selection_type: [
    alphacode + none,
    alphacode + public_private_slow_5,
    corpus_instrument_fuzz_mutator_with_constraint_per_solution + slow_5,
]

### Evaluation
```python
python scripts/evaluate/usefulness/finetune_exp/eval.py
```

## Intermediate results
intermediate results are stored in `code-contest-exp/results/pie`

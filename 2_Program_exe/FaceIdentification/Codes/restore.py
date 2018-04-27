import tensorflow as tf
import sys

# the command to use thif file:
# python restore.py <testImagePath>

folderPath = 'D:/Files/FYP_Project/Server/identificationFile/FaceDatabase/'
filePath = folderPath + 'att_faces_10_people/'
modelPath = 'D:/Files/FYP_Project/Server/identificationFile/Codes/Model/'
imgPath = sys.argv[1]
imSize = [112, 92]

#tf.reset_default_graph()

with tf.Session() as sess:


	sess.run(tf.global_variables_initializer())

	saver = tf.train.import_meta_graph(modelPath+"model.ckpt.meta")
	saver.restore(sess, tf.train.latest_checkpoint(modelPath))

	g = tf.get_default_graph()

	# print(sess.run(tf.get_default_graph().get_tensor_by_name("Variable_1:0")))


	with tf.gfile.FastGFile(imgPath, 'rb') as f:
		image_raw_data_jpg = f.read()

	img_data_jpg = tf.image.decode_jpeg(image_raw_data_jpg)
	img_data_jpg = tf.image.convert_image_dtype(img_data_jpg, dtype=tf.uint8)
	img_data_jpg = tf.cast(img_data_jpg, tf.int32)
	# dtype is changed to tf.int32 instead of tf.uint8 cause not supported on this computer
	img_data_jpg = tf.reduce_mean(img_data_jpg, 2)
	img_data_jpg = tf.reshape(img_data_jpg, [imSize[0]*imSize[1]])
	
	imgSet_mean = g.get_tensor_by_name("Cast_100:0")
	er = g.get_tensor_by_name("Diag:0")
	Ur = g.get_tensor_by_name("MatMul_1:0")
	output = g.get_tensor_by_name("truediv_34:0")

	A = img_data_jpg - imgSet_mean
	A = tf.stack(A,0)	
	A = tf.transpose(A)
	A = tf.reshape(A, [imSize[0]*imSize[1], 1])
	A = tf.cast(A, tf.float32)

	eigen_weights = tf.transpose(tf.matmul(tf.matmul(tf.matrix_inverse(er), tf.transpose(Ur)), A))

	result = tf.argmax(output, 1)+1
	# print(sess.run(output, feed_dict={g.get_tensor_by_name("Placeholder:0"):eigen_weights.eval()}))
	print(sess.run(result[0], feed_dict={g.get_tensor_by_name("Placeholder:0"):eigen_weights.eval()}))



